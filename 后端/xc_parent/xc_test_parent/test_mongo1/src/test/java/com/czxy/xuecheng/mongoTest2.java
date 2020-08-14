package com.czxy.xuecheng;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhandehuang@itcast.cn
 * @version 1.0
 * @date 2020/6/15 0015
 **/
public class mongoTest2 {
    /**
     * 方式1：连接本地数据库
     */
    @Test
    public void testconn() {
        //采用连接字符串
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        System.out.println(mongoClient);
    }

    /**
     * 方式2：采用连接字符串
     */
    @Test
    public void testconn2() {
        //获得连接
        //1.拼接连接字符串
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://root:root@localhost:27017/demo");
        //2.获得连接
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        System.out.println(mongoClient);
    }

    /**
     * 查询第一条
     */
    @Test
    public void testFindOne() {
        //查询一个
        //1.获得连接
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://root:root@localhost:27017/demo");
        MongoClient mongoClient = new MongoClient(mongoClientURI);

        //2.获得数据库
        MongoDatabase database = mongoClient.getDatabase("demo");

        //3.获得集合
        MongoCollection<Document> student = database.getCollection("book");
        //4.查询操作
        Document document = student.find().first();
        //5.将文档转换json,并输出
        System.out.println(document.toJson());
    }


    /**
     * 创建集合
     */
    @Test
    public void testCreateColl() {
        //1.获得连接
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://root:root@localhost:27017");
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        //2.获得数据库
        MongoDatabase database = mongoClient.getDatabase("demo");
        //创建连接
        database.createCollection("teacher");
    }

    /**
     * 添加文档
     */
    @Test
    public void testinsert() {
        //1.获得连接
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://root:root@localhost:27017");
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        //2.获得数据库
        MongoDatabase database = mongoClient.getDatabase("demo");
        //3.获得集合
        MongoCollection<Document> collection = database.getCollection("teacher");
        //4.准备文档
        Document document = new Document();
        document.append("username", "jack");
        document.append("password", "1234");
        document.append("age", 18);
        //5.录入文档
        collection.insertOne(document);

    }

    /**
     * 批量插入文档
     */
    @Test
    public void testinserts() {
        //1.获得连接
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://root:root@localhost:27017");
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        //2.获得数据库
        MongoDatabase database = mongoClient.getDatabase("demo");
        //3.获得集合
        MongoCollection<Document> collection = database.getCollection("teacher");
        //4.准备文档
        Document document = new Document();
        document.append("username", "aca");
        document.append("password", "434");
        document.append("age", 28);

        Document document2 = new Document();
        document2.append("username", "aab");
        document2.append("password", "234");
        document2.append("age", 43);

        List<Document> list = new ArrayList<>();
        list.add(document);
        list.add(document2);

        //5.录入文档
        collection.insertMany(list);

    }

    /**
     * 查询所有
     */
    @Test
    public void testFindall() {
        //1.获得连接
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://root:root@localhost:27017");
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        //2.获得数据库
        MongoDatabase database = mongoClient.getDatabase("demo");
        //3.获得集合
        MongoCollection<Document> collection = database.getCollection("teacher");
        //4.查询所有
        FindIterable<Document> findIterable = collection.find();
        //5.处理数据(遍历迭代器)
        MongoCursor<Document> it = findIterable.iterator();
        while (it.hasNext()) {
            Document document = it.next();
            String username = document.get("username", String.class);
            String password = document.get("password", String.class);
            Integer age = document.get("age", Integer.class);
            System.out.println(username + "_" + password + "_" + age);
        }

    }


    /**
     * 更新
     */
    @Test
    public void testupdate() {
        //1.采用连接字符串
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://root:root@localhost:27017/");
        MongoClient mongoClient = new MongoClient(mongoClientURI);

        //2.获得数据库
        MongoDatabase demo = mongoClient.getDatabase("demo");
        //3.获得集合
        MongoCollection<Document> teacher = demo.getCollection("teacher");
        //4.更新
        teacher.updateOne(Filters.eq("age", 18), new Document("$set", new Document("name", "aaa")));

    }

    /**
     *替换文档
     */
    @Test
    public void testReplace(){
        //1.获得连接
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://root:root@localhost:27017");
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        //2.获得数据库
        MongoDatabase demo = mongoClient.getDatabase("demo");
        //3.获得集合
        MongoCollection<Document> teacher = demo.getCollection("teacher");
        //4.更新
        Document doc = new Document("password", "999");
        teacher.replaceOne(Filters.eq("username","tom"),doc);
    }



    /**
     * 删除文档
     */
@Test
    public void testdelete(){
        //1.获得连接
    MongoClientURI mongoClientURI = new MongoClientURI("mongodb://root:root@localhost:27017");
    MongoClient mongoClient = new MongoClient(mongoClientURI);
    //2.获得数据库
    MongoDatabase demo = mongoClient.getDatabase("demo");
    //3.获得集合
    MongoCollection<Document> teacher = demo.getCollection("teacher");
    //4.删除
     teacher.deleteOne(Filters.eq("username","jack"));
    }

}
