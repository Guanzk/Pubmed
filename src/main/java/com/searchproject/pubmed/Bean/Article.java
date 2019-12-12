package com.searchproject.pubmed.Bean;

/**
 * 数据源 articlesSimple
 * 数据格式
 * 作者著作PMID ； 文章出版年份Pubyear ； 文章标题ArticleTitle； 文章参与作者数量AuthorNum;
 */

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * author: @DrRic
 */

@Data
@Entity
@Table(name="article_simple")
public class Article {

    //根据aid所检索获得的PMID进行文章相关信息的检索
    @Id
    private String pmid;

    //查询文章出版年份、标题及参与撰写的作者数量
    private String pubyear;
    private String article_title;
    private Integer author_num;

    public Article() {
    }

    public Article(int pmid, String pubyear, String articleTitle, int authorNum) {
        pmid = pmid;
        this.pubyear = pubyear;
        article_title = articleTitle;
        author_num = authorNum;
    }

    //返回所获得的结果集，为单行数据
    @Override
    public String toString() {
        String ans = "";
        ans += "PMID:" + pmid + " Pubyear:" + pubyear + " ArticleTile:" + article_title + " AuthorNum" + author_num;
        return ans;
    }

    public void setPmid(String PMID) {
        this.pmid = PMID;
    }

    public String getPmid() {
        return this.pmid;
    }

    public void setPubyear(String pubyear) {
        this.pubyear = pubyear;
    }

    public String getPubyear() {
        return pubyear;
    }

    public void setArticle_title(String articleTile) {
        article_title = articleTile;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setAuthor_num(Integer author_num) {
        this.author_num = author_num;
    }

    public Integer getAuthor_num() {
        return author_num;
    }

}
