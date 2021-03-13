###### Note:

I am using a Mac with Kafka binaries setup and added to `.zshrc` file like:

`export PATH="$PATH:$HOME/Softwares/kafka_2.13-2.7.0/bin"`

###### Expected that:

Zookeeper and Kafka is running on your local machine:

- ```cd <your_kafka_binaries_root_dir>```
- ```zookeeper-server-start.sh config/zookeeper.properties```
- ```kafka-server-start config/server.properties```

Following topic should be created on your local machine:

```shell
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic second_topic --partitions 3 --replication-factor 1
```