package com.searchproject.pubmed.Bean;

/**
 * 数据源 articlesSimple
 * 数据格式
 * 作者著作PMID ； 文章出版年份Pubyear ； 文章标题ArticleTitle； 文章参与作者数量AuthorNum;
 */

/**
 * author: @DrRic
 */

public class Article {

    //根据aid所检索获得的PMID进行文章相关信息的检索
    private int PMID;

    //查询文章出版年份、标题及参与撰写的作者数量
    private String Pubyear;
    private String ArticleTitle;
    private int AuthorNum;

    public Article() {
    }

    public Article(int pmid, String pubyear, String articleTitle, int authorNum) {
        PMID = pmid;
        Pubyear = pubyear;
        ArticleTitle = articleTitle;
        AuthorNum = authorNum;
    }

    //返回所获得的结果集，为单行数据
    @Override
    public String toString() {
        String ans = "";
        ans += "PMID:" + PMID + " Pubyear:" + Pubyear + " ArticleTile:" + ArticleTitle + " AuthorNum" + AuthorNum;
        return ans;
    }

    public void setPMID(int PMID) {
        this.PMID = PMID;
    }

    public int getPMID() {
        return this.PMID;
    }

    public void setPubyear(String pubyear) {
        Pubyear = pubyear;
    }

    public String getPubyear() {
        return Pubyear;
    }

    public void setArticleTitle(String articleTile) {
        ArticleTitle = articleTile;
    }

    public String getArticleTitle() {
        return ArticleTitle;
    }

    public void setAuthorNum(int authorNum) {
        AuthorNum = authorNum;
    }

    public int getAuthorNum() {
        return AuthorNum;
    }

}
