import os
import logging
import gzip
import argparse
import sys

import paddle.v2 as paddle
import reader
from seq2seq import encoder_decoder_network
import pdb

logger = logging.getLogger("seq2seq")
logger.setLevel(logging.INFO)


def save_model(trainer, save_path, parameters):
    with gzip.open(save_path, 'w') as f:
        trainer.save_parameter_to_tar(f)


def load_initial_model(model_path, parameters):
    with gzip.open(model_path, 'rb') as f:
        parameters.init_from_tar(f)


def parse_args():
    """
    Parse input arguments
    """
    parser = argparse.ArgumentParser(description='Train a couplet generate network')
    parser.add_argument('--num_passes', dest='num_passes',
                        help='Number of passes for the training task.',
                        default=10, type=int)
    parser.add_argument('--batch_size', dest='batch_size',
                        help='The number of training examples in one forward/backward pass.',
                        default=16, type=int)
    parser.add_argument('--use_gpu', dest='use_gpu',
                        help='Whether to use gpu to train the model.',
                        default=False, type=bool)
    parser.add_argument('--trainer_count', dest='trainer_count',
                        help='The thread number used in training.',
                        default=1, type=int)
    parser.add_argument('--save_dir_path', dest='save_dir_path',
                        help='The path to saved the trained models.',
                        default="models", type=str)
    parser.add_argument('--encoder_depth', dest='encoder_depth',
                        help='The number of stacked LSTM layers in encoder.',
                        default=3, type=int)
    parser.add_argument('--decoder_depth', dest='decoder_depth',
                        help='The number of stacked LSTM layers in decoder.',
                        default=3, type=int)
    parser.add_argument('--train_data_path', dest='train_data_path',
                        help='The path of trainning data.',
                        default="data/train/in.txt", type=str)
    parser.add_argument('--train_label_path', dest='train_label_path',
                        help='The path of trainning label.',
                        default="data/train/out.txt", type=str)
    parser.add_argument('--vocab_path', dest='vocab_path',
                        help='The path of word vocabulary.',
                        default="data/vocabs.txt", type=str)
    parser.add_argument('--init_model_path', dest='init_model_path',
                        help='The path of a trained model used to initialized all the model parameters.',
                        default="", type=str)

    if len(sys.argv) == 1:
        parser.print_help()
        sys.exit(1)

    args = parser.parse_args()
    return args


def train(num_passes,
          batch_size,
          use_gpu,
          trainer_count,
          save_dir_path,
          encoder_depth,
          decoder_depth,
          train_data_path,
          train_label_path,
          vocab_path,
          init_model_path=""):
    if not os.path.exists(save_dir_path):
        os.makedirs(save_dir_path)
    assert os.path.exists(vocab_path), "The vocabs txt file needed!"
    assert os.path.exists(train_data_path), "The train data file needed!"
    assert os.path.exists(train_label_path), "The train label file needed!"

    # initialize PaddlePaddle
    paddle.init(use_gpu=use_gpu, trainer_count=trainer_count)

    # define optimizer
    optimizer = paddle.optimizer.Adam(
        learning_rate=1e-4,
        regularization=paddle.optimizer.L2Regularization(rate=1e-5),
        model_average=paddle.optimizer.ModelAverage(
            average_window=0.5, max_average_window=2500))

    # define topology
    cost = encoder_decoder_network(
        word_count=len(open(vocab_path, "r").readlines()),
        emb_dim=512,
        encoder_depth=encoder_depth,
        encoder_hidden_dim=512,
        decoder_depth=decoder_depth,
        decoder_hidden_dim=512,
        bos_id=0,
        eos_id=1,
        max_length=50)

    # create parameters
    parameters = paddle.parameters.create(cost)

    # load initial model
    if init_model_path:
        load_initial_model(init_model_path, parameters)

    # define trainer
    trainer = paddle.trainer.SGD(cost=cost,
                                 parameters=parameters,
                                 update_equation=optimizer)

    # define data reader
    train_reader = paddle.batch(
        paddle.reader.shuffle(
            reader.train_reader(train_data_path, train_label_path, vocab_path),
            buf_size=10240),
        batch_size=batch_size)

    # define the event_handler callback
    def event_handler(event):
        if isinstance(event, paddle.event.EndIteration):
            if (not event.batch_id % 10000) and event.batch_id:
                save_path = os.path.join(save_dir_path,
                                         "pass_%05d_batch_%05d.tar.gz" %
                                         (event.pass_id, event.batch_id))
                save_model(trainer, save_path, parameters)

            if not event.batch_id % 10:
                logger.info("Pass %d, Batch %d, Cost %f, %s" % (
                    event.pass_id, event.batch_id, event.cost, event.metrics))

        if isinstance(event, paddle.event.EndPass):
            save_path = os.path.join(save_dir_path,
                                     "pass_%05d.tar.gz" % event.pass_id)
            save_model(trainer, save_path, parameters)

    # start training
    trainer.train(
        reader=train_reader, event_handler=event_handler, num_passes=num_passes)


if __name__ == "__main__":
    args = parse_args()
    print(args)

    train(args.num_passes,
          args.batch_size,
          args.use_gpu,
          args.trainer_count,
          args.save_dir_path,
          args.encoder_depth,
          args.decoder_depth,
          args.train_data_path,
          args.train_label_path,
          args.vocab_path,
          args.init_model_path)
