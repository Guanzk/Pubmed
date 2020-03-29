package com.searchproject.pubmed.test;

import org.springframework.data.annotation.PersistenceConstructor;

import java.io.*;
import java.util.*;

//考虑文件超过内存空间，将每一年名单hashcode分散到小文件，直至小文件空间小于内存。每次比较三年相同文件hashcode编码的,得到相同人员名单后进行排序，然后输出到文件。最后再将多份相同人员名单进行归并排序
//TODO 多线程
public class Solve {
    private final static int MAX_AGE=100;
    private final static int MIN_AGE=18;

    private final static String[]names={"赵","钱","孙","李","周","吴","郑","王","冯","陈","褚","卫","蒋","沈","韩","杨","朱","秦","尤","许",
            "何","吕","施","张","孔","曹","严","华","金","魏","陶","姜","戚","谢","邹","喻","柏","水","窦","章","云","苏","潘","葛","奚","范","彭","郎",
            "鲁","韦","昌","马","苗","凤","花","方","俞","任","袁","柳","酆","鲍","史","唐","费","廉","岑","薛","雷","贺","倪","汤","滕","殷",
            "罗","毕","郝","邬","安","常","乐","于","时","傅","皮","卞","齐","康","伍","余","元","卜","顾","孟","平","黄","和",
            "穆","萧","尹","姚","邵","湛","汪","祁","毛","禹","狄","米","贝","明","臧","计","伏","成","戴","谈","宋","茅","庞","熊","纪","舒"};
    private final static int flushLines=10000;//应该根据内存大小确定
    //生成数据
    public static void  generateData(int total,String fileName) throws IOException {
        Random random=new Random();
        File file=new File(fileName);
        FileOutputStream fos=new FileOutputStream(file);
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(fos));
        for(int i=0;i<total;i++){
            int age=random.nextInt(MAX_AGE-MIN_AGE+1)+MIN_AGE;
            String name= generateName();
            String id=String.valueOf(name.hashCode());
            String phone="123";
            String company="abc";
            String line=name+","+age+","+id+","+phone+","+company;
            writer.write(line+"\r\n");


        }
        writer.close();
        fos.close();
    }
    //多路归并读取数据
    public static List<Person> readData(BufferedReader[] file) throws IOException {
        List<Person>list=new ArrayList<>();
        for(BufferedReader br:file){
            String line=br.readLine();
            if(line!=null){
                list.add(new Person(line));
            }else list.add(null);
        }
        return list;

    }
    //解决问题
    public void solve(List<String>fileList) throws IOException {
        Runtime runtime=Runtime.getRuntime();
        long freeMem=runtime.freeMemory();
        long maxFileMem=0;
        for(String file:fileList){
            File f=new File(file);
            if(!f.exists()||!f.isFile()){
                throw  new IOException("错误路径");
            }
            maxFileMem=Math.max(f.length(),maxFileMem);
        }
        int partiNum=(int)(maxFileMem/freeMem/fileList.size());
        partiFile(fileList,partiNum);
        List<String>overlapFiles=generateOverLayPerson(fileList,partiNum);
        merge(overlapFiles);


    }

    //归并并输出得到最终结果
    private void merge(List<String> overlapFiles) throws IOException {
        BufferedReader[]readers=new BufferedReader[overlapFiles.size()];
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("result.txt")));
        for(int i=0;i<overlapFiles.size();i++){
            readers[i]=new BufferedReader(new InputStreamReader(new FileInputStream(overlapFiles.get(i))));
        }
        int len=1;
        while(true){
            List<Person> arr=readData(readers);
            int i=0;
            Person min=null;
            while(i<arr.size()&&arr.get(i)==null)i++;
            if(i<arr.size())min=arr.get(i);
            if(i==arr.size())break;
            for(;i<arr.size();i++){
                Person p=arr.get(i);
                if(p!=null&&p.getAge()>min.getAge())min=p;
            }
            bw.write(min.toString()+"\r\n");

        }
        bw.flush();
        bw.close();
        for(int i=0;i<readers.length;i++)readers[i].close();

    }

    //得到重叠人员，返回文件名
    private List<String> generateOverLayPerson(List<String> fileList, int partiNum) throws IOException {
        List<String>res=new ArrayList<>();
        for(int i=0;i<partiNum;i++){
            HashSet<Person> set1=new HashSet<>();
            String line="";
            String f0=fileList.get(0)+"_"+i;
            BufferedReader br0=new BufferedReader(new InputStreamReader(new FileInputStream(f0)));
            while((line=br0.readLine())!=null){
                Person p=new Person(line);
                set1.add(p);
            }

            for(int j=1;i<fileList.size();i++){
                HashSet<Person> set2=new HashSet<>();
                String f1=fileList.get(j)+"_"+i;

                BufferedReader br1=new BufferedReader(new InputStreamReader(new FileInputStream(f1)));


                while((line=br1.readLine())!=null){
                    Person p=new Person(line);
                    set2.add(p);
                }
                set1.retainAll(set2);

            }
            List<Person>sortedSet=sort(set1);
            for(Person p:set1){
                BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("overlap_"+partiNum)));
                res.add("overlap_"+partiNum);
                bw.write(p.toString()+"\r\n");
            }


        }
        return res;
    }

    //快排
    private List<Person> sort(HashSet<Person> set1) {
        List<Person>list=new ArrayList<Person>(set1);
        qiuckSort(list);
        return list;
    }
    public static List<Person> qiuckSort(List<Person> array){
        if (array.size() <=1) return array;
        qiuck(array,0,array.size()-1);
        return array;
    }
    private static void qiuck(List<Person> array ,int start, int end){
        if(start >end) return;
        int key = selectionKey(array,start,end);
        qiuck(array,start,key-1);
        qiuck(array,key+1,end);
    }
    private static int selectionKey(List<Person> array, int start, int end){
        int random = (int)(Math.random()*(end-start+1)+start);
        swap(array,random,start);
        //找到一个随机key
        Person value = array.get(start);
        //从头开始往后[start+1,i-1]
        int i = start+1;
        //从尾开始往前[j+1,end]
        int j = end;
        while (true){
            //相当于把等于key的值均分到两边
            while (i<=end && array.get(i).compareTo( value)<0 ) i++;
            while (j>=start+1 &&array.get(j).compareTo( value)>0 ) j--;
            //交换后两个指针都移动一步
            if(i>j) break;
            swap(array,i,j);
            i++;j--;
        }
        swap(array,start,j);
        return j;
    }

    private static void swap(List<Person> array, int i, int j) {
        Person temp=array.get(i);
        array.set(i,array.get(j));
        array.set(j,temp);
    }

    //切分文件到内存可以容纳的大小
    private void partiFile(List<String> fileList, int partiNum) throws IOException {
        for(String file:fileList){
            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            BufferedWriter[]outputWriters=new BufferedWriter[partiNum];
            for(int i=0;i<partiNum;i++){
                outputWriters[i]=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file+"_"+i)));
            }
            String line="";
            List<String>strings=new LinkedList<>();
            int cnt=0;
            while((line=br.readLine())!=null){
                cnt++;
                strings.add(line);
                if(strings.size()==flushLines){
                    writeData(outputWriters,strings,partiNum);

                }
            }
          writeData(outputWriters,strings,partiNum);


        }

    }

    //切分写入
    private void writeData(BufferedWriter[] outputWriters, List<String> strings,int partiNum) throws IOException {
        for(String l:strings){
            int k=l.split(",")[0].hashCode()%partiNum;
            outputWriters[k].write(l+"\r\n");
            strings=new LinkedList<>();
        }
    }

    private static String generateName() {
        String res="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            res+=names[random.nextInt(names.length)];
        }
        return res;
    }

    public static void main(String[] args) {
        try {
            generateData(100,"d://2006.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
class Person{
    private String name;
    private int age;
    private String id;
    private String phone;
    private String company;
    public Person(String name, int age, String id, String phone, String company) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.phone = phone;
        this.company = company;
    }

    public Person(String line) {
        String[]lines=line.split(",");
        name=lines[0];
        age=Integer.parseInt(lines[1]);
        id=lines[2];
        phone=lines[3];
        company=lines[4];

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id==person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, id, phone, company);
    }
    public int compareTo(Person p){
        return p.age-age;
    }
    public String getName() {
        return name;
    }
    public String toString(){
        return name+","+age+","+id+","+phone+","+company;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }



}