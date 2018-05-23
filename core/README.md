# 核心

Auto generate couplet by using baidu ML framework paddlepaddle

----

## 使用说明


### train

```bash
cd mlcouplet/core
python train.py --num_passes 100 \
    --batch_size 64 \
    --use_gpu True \
    --trainer_count 1 \
    --save_dir_path model/ \
    --train_data_path data/train/in.txt \
    --train_label_path data/train/out.txt \
    --vocab_path data/vocabs.txt
```

### test

```bash
python test.py --model_path model/pass_00040.tar.gz \
    --vocabs_path data/vocabs.txt \
    --test_data_path data/test.txt \
    --save_file data/output.txt \
    --beam_size 5
```

* vocabs_path:数据集词文件
* test_data_path: 上联文件，其中包含一行不超过50字的上联（可以包含“，”和“。”）
* save_file: 输出文件，包括上联和排序靠前的5条输出
