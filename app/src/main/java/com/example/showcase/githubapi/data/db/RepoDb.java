package com.example.showcase.githubapi.data.db;

import com.example.showcase.githubapi.data.api.model.RepoApi;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "repo")
public class RepoDb {

    @Id(autoincrement = true) private Long id;
    @Property(nameInDb = "name") private String name;
    @Property(nameInDb = "owner_login") private String owner_login;
    @Property(nameInDb = "html_url") private String html_url;
    @Property(nameInDb = "description") private String description;
    @Property(nameInDb = "url") private String url;
    @Property(nameInDb = "stargazers_count") private int stargazers_count;
    @Property(nameInDb = "language") private String language;
    @Property(nameInDb = "forks_count") private int forks_count;

    @Generated(hash = 1798356183)
    public RepoDb(Long id, String name, String owner_login, String html_url, String description, String url,
            int stargazers_count, String language, int forks_count) {
        this.id = id;
        this.name = name;
        this.owner_login = owner_login;
        this.html_url = html_url;
        this.description = description;
        this.url = url;
        this.stargazers_count = stargazers_count;
        this.language = language;
        this.forks_count = forks_count;
    }

    @Generated(hash = 1571608222)
    public RepoDb() {
    }

    public static RepoDb fromRepoApi(RepoApi repoApi) {
        RepoDb dbRepoDb = new RepoDb();

        dbRepoDb.setName(repoApi.getName());
        dbRepoDb.setOwner_login(repoApi.getOwner().getLogin());
        dbRepoDb.setHtml_url(repoApi.getHtml_url());
        dbRepoDb.setDescription(repoApi.getDescription());
        dbRepoDb.setUrl(repoApi.getUrl());
        dbRepoDb.setStargazers_count(repoApi.getStargazers_count());
        dbRepoDb.setLanguage(repoApi.getLanguage());
        dbRepoDb.setForks_count(repoApi.getForks_count());

        return dbRepoDb;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner_login() {
        return owner_login;
    }

    public void setOwner_login(String owner_login) {
        this.owner_login = owner_login;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }
}
