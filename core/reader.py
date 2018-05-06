

def read_vocab(vocab_file):
    """generate vocabularies list"""
    f = open(vocab_file, 'rb')
    vocabs = [line.decode('utf8')[:-1] for line in f]
    f.close()
    return vocabs


def load_dict(vocab_file):
    """generate word dictionary with (key:value) -> (word, index)"""
    vocabs = read_vocab(vocab_file)
    word_dict = dict((c, i) for i, c in enumerate(vocabs))
    return word_dict


def test_load_dict():
    word_dict = load_dict('data/vocabs.txt')
    print(len(word_dict))
    for k, v in word_dict.items():
        print(k)
        print(v)


def encode_text(words, vocab_indices):
    word_ids = []
    for word in words:
        if word in vocab_indices:
            word_ids.append(vocab_indices[word])
        else:
            word_ids.append(vocab_indices['<unk>'])
    return word_ids


def decode_text(labels, vocabs, end_token='<e>'):
    results = []
    for idx in labels:
        word = vocabs[idx]
        if word == end_token:
            return ' '.join(results)
        results.append(word)
    return ' '.join(results)


def train_reader(data_file, label_file,  vocab_file):
    """define reader in paddlepaddle to read couplet"""
    def reader():
        word_dict = load_dict(vocab_file)
        bos = '<s>'  # begin of string
        eos = '<e>'  # end of string

        input_file = open(data_file, 'r')
        target_file = open(label_file, 'r')

        for input_line in input_file:
            input_line = input_line.decode('utf-8')[:-1]
            target_line = target_file.readline().decode('utf-8')[:-1]
            input_words = [x for x in input_line.split(' ') if x != '']
            if len(input_words) >= 50:
                input_words = input_words[:50-1]
            input_words = [bos, ] + input_words + [eos]
            target_words = [x for x in target_line.split(' ') if x != '']
            if len(target_words) >= 50:
                target_words = target_words[:50-1]
            target_words = [bos, ] + target_words + [eos]
            in_seq = encode_text(input_words, word_dict)
            target_seq = encode_text(target_words, word_dict)
            yield in_seq, target_seq[:-1], target_seq[1:]

    return reader


def test_train_reader(data_file, label_file,  vocab_file):
    word_dict = load_dict(vocab_file)
    bos = '<s>'
    eos ='<e>'

    input_file = open(data_file, 'r')
    target_file = open(label_file, 'r')

    for input_line in input_file:
        input_line = input_line.decode('utf-8')[:-1]
        target_line = target_file.readline().decode('utf-8')[:-1]
        input_words = [x for x in input_line.split(' ') if x != '']
        if len(input_words) >= 50:
            input_words = input_words[:50 - 1]
        input_words = [bos, ] + input_words + [eos]
        target_words = [x for x in target_line.split(' ') if x != '']
        if len(target_words) >= 50:
            target_words = target_words[:50 - 1]
        target_words = [bos, ] + target_words + [eos]
        in_seq = encode_text(input_words, word_dict)
        target_seq = encode_text(target_words, word_dict)

        print(in_seq)
        print(target_seq)


def test_reader(data_file_path, vocab_file):
    def reader():
        word_dict = load_dict(vocab_file)

        bos = "<s>"
        eos = "<e>"

        with open(data_file_path, "r") as f:
            for line in f:
                input_words = [x for x in line.strip().decode("utf8", errors="ignore").split()]
                input_words = [bos, ] + input_words + [eos]
                in_seq = encode_text(input_words, word_dict)
                yield in_seq

    return reader


def test_test_reader(data_file_path, vocab_file):
    word_dict = load_dict(vocab_file)

    bos = "<s>"
    eos = "<e>"

    with open(data_file_path, "r") as f:
        for line in f:
            input_line = "".join(line.strip().decode("utf8", errors="ignore").split())
            input_words = [x for x in input_line]
            input_words = [bos, ] + input_words + [eos]
            in_seq = encode_text(input_words, word_dict)
            print(in_seq)

if __name__ == "__main__":
    # test_train_reader('data/train/in.txt', 'data/train/out.txt', 'data/vocabs.txt')
    # test_load_dict()
    test_test_reader('data/test.txt', 'data/vocabs.txt')

