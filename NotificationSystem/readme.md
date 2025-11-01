---

# 📨 Simple Kafka Producer in Java

This Java program connects to a **Kafka broker**, creates a **message (event)**, and sends it to a **Kafka topic** named `user-events`.

---

## 🧠 Line-by-Line Explanation

### 1️⃣ Import Statements

```java
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
```

These imports bring in Kafka classes:

* **KafkaProducer** → the actual producer client that sends messages.
* **ProducerRecord** → the message (key-value pair) you send to Kafka.
* **ProducerConfig** → constants used to configure the producer.
* **StringSerializer** → converts Strings into bytes before sending (Kafka only works with bytes).

---

### 2️⃣ Create a Properties Object

```java
Properties props = new Properties();
```

This `props` object holds **Kafka configuration details** — like where the Kafka server is and how data should be serialized.

---

### 3️⃣ Define Kafka Producer Configurations

```java
props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
```

This tells Kafka **where your Kafka broker is running**.
`localhost:9092` → means Kafka is running locally on your computer, port `9092`.

```java
props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
```

Kafka only transfers **bytes**, not strings or objects.
So before sending, we must tell Kafka **how to convert (serialize)** the data.

Here both the **key** and **value** will be converted from `String → bytes` using `StringSerializer`.

---

### 4️⃣ Create KafkaProducer Instance

```java
try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
```

This creates a **KafkaProducer** object with your configuration.

* `<String, String>` means:

    * Key type = `String`
    * Value type = `String`
* It’s inside a **try-with-resources** block, meaning the producer will automatically close when done (no memory leaks).

---

### 5️⃣ Create a Message (ProducerRecord)

```java
String topic = "user-events";
String key = "user1";
String value = "User created successfully!";

ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
```

A **ProducerRecord** represents one message/event you want to send.

It contains:

* **topic** → where to send the message (`user-events`)
* **key** → optional; helps Kafka decide which partition to store the message in (all same keys go to same partition)
* **value** → actual message (your data)

👉 So, here the message says:

> Send `"User created successfully!"` to topic `"user-events"` with key `"user1"`.

---

### 6️⃣ Send the Message

```java
producer.send(record, (metadata, exception) -> {
    if (exception == null) {
        System.out.println("✅ Sent message to topic " + metadata.topic() +
                " partition " + metadata.partition() +
                " offset " + metadata.offset());
    } else {
        exception.printStackTrace();
    }
});
```

The `send()` method sends the message **asynchronously** (non-blocking).
It takes a **callback** that runs when Kafka finishes sending the message.

**Callback parameters:**

* `metadata` → contains details of where the message was written (topic, partition, offset)
* `exception` → contains any error if sending failed

✅ If everything is fine, it prints where your message was stored, like:

```
✅ Sent message to topic user-events partition 0 offset 12
```

---

### 7️⃣ Flush and Close Producer

```java
producer.flush();
```

`flush()` makes sure all pending messages are actually sent to Kafka before closing.
Since **try-with-resources** is used, the producer automatically closes after the block ends.

---

## 🧠 Summary of Flow

| Step | Action                  | Purpose                           |
| ---- | ----------------------- | --------------------------------- |
| 1    | Set properties          | Define Kafka server & serializers |
| 2    | Create `KafkaProducer`  | Connect to Kafka                  |
| 3    | Create `ProducerRecord` | Prepare message                   |
| 4    | Send message            | Asynchronously push data to Kafka |
| 5    | Print metadata          | Confirm where message was written |
| 6    | Flush & close           | Clean up resources                |

---

## 💡 What Happens Behind the Scenes

1. Producer sends message →
2. Kafka Broker receives it →
3. Stores it in a **partition** of the topic →
4. Kafka assigns an **offset** (like a message ID).

The **offset** helps consumers read messages **in order** later.

---

## 🧩 Example Output

```
✅ Sent message to topic user-events partition 0 offset 15
```

This means your message went to:

* **Topic** → `user-events`
* **Partition** → `0`
* **Offset** → `15` *(its unique message number inside that partition)*

---