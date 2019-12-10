package com.searchproject.pubmed.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.searchproject.pubmed.Bean.Dimensions_data;
import com.searchproject.pubmed.Bean.Info_card;
import com.searchproject.pubmed.Bean.Root;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class SearchProduct {
    public static String search(String product){
      String airpodsString="{\t\n" +
              "\t\t    \"name\": \"AirPods\",\n" +
              "\t\t\t\"dimension\": \"recommended product\",\n" +
              "\t\t\t\"data\": [\n" +
              "\t\t\t\t\"product: airpods\",\n" +
              "\t\t\t\t\"description: The new Air Pods not only combine intelligent design and breakthrough technology, but also have pure and clear sound quality. It is equipped with the new Apple H1 headset chip, which allows you to activate Siri with your voice without hands.\",\n" +
              "\t\t\t\t\"price: 1199\"\n" +
              "\t\t\t],\n" +
              "\t\t\t\"imgName\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574850977681&di=f64c0f9ec1adf6e356aadad4c86fdc52&imgtype=0&src=http%3A%2F%2Fcontent.image.alimmdn.com%2Fcms%2F13859%2F153967831166791818615177.jpg\",\n" +
              "\t\t\t\"url\": \"http://t.cn/Aidu9fjO\"\n" +
              "\t}";
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        String xiaoaiString="{\n" +
                "\t\t\t\"name\": \"xiaoai\",\n" +
                "\t\t\t\"dimension\": \"similar product\",\n" +
                "\t\t\t\"data\": [\n" +
                "\t\t\t\t\"product: XiaoMi AI Speaker\",\n" +
                "\t\t\t\t\"description: Xiaoai AI Speaker is a smart speaker developed by Xiaomi. It can play music, radio on demand, but also provides comics, novels, talk shows, education and learning, children's audio books and many other content. Simply interact with the mini version to control all IoT devices in the home.\",\n" +
                "\t\t\t\t\"price: 299\"\n" +
                "\t\t\t],\n" +
                "\t\t\t\"imgName\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574850921698&di=faf7ccf9f11714dd8da3bf27180f9046&imgtype=0&src=http%3A%2F%2Fpic1.58cdn.com.cn%2Fzhuanzh%2Fn_v25d5f88c08a594b68a54e7ea27e896573.jpg%3Fw%3D750%26h%3D0\",\n" +
                "\t\t\t\"url\": \" https://item.jd.com/5239477.html\"\n" +
                "\t}";
        Dimensions_data xiaoai=gson.fromJson(xiaoaiString,Dimensions_data.class);
        Dimensions_data airpods=gson.fromJson(airpodsString,Dimensions_data.class);
        List<Dimensions_data>dimensions_data=new LinkedList<>();
        dimensions_data.add(airpods);
        dimensions_data.add(xiaoai);
        String info_cardString="{\"name\": \"earphone\",\n" +
                "\t\t\"type\": \"Electronic products\",\n" +
                "\t\t\"description\": \"There is delicate and exquisite feeling in wen run of simple sense.The saturation purity of color is also very full. No more headaches trying to untangle a tangled cord.It feels light on the ear.One word about AirPods and apple devices is silky smooth. This pairing with your own device will give you a whole new perspective on bluetooth pairing.If you're a regular bluetooth headset user and haven't used AirPods, the pairing is enough to get you to say -- oh my god! Yes, those are the words!\"}";
        Info_card info_card=gson.fromJson(info_cardString,Info_card.class);
        Root root=new Root();
        root.setInfo_card(info_card);
        root.setDimensions_data(dimensions_data);
        String json=gson.toJson(root);
        log.debug(json);
        return gson.toJson(root);
    }

//    public static void main(String[] args) {
//        System.out.println(SearchProduct.search("a"));
//    }
}
