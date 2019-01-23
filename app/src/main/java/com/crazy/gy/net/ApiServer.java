package com.crazy.gy.net;


import com.crazy.gy.bean.Article;
import com.crazy.gy.bean.BannerBean;
import com.crazy.gy.bean.DataResponse;
import com.crazy.gy.bean.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * author: fangxiaogang
 * date: 2018/9/1
 */

public interface ApiServer {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<DataResponse<User>> login(@Field("username") String username, @Field("password") String password);



    /**
     * 注册
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @POST("/user/register")
    @FormUrlEncoded
    Observable<DataResponse<User>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);




    /**
     * 首页Banner
     * @return
     */
    @GET("/banner/json")
    Observable<DataResponse<List<BannerBean>>> getHomeBanners();



    /**
     * 首页数据
     * @param page
     * @return
     */
    @GET("/article/list/{page}/json")
    Observable<DataResponse<Article>> getHomeArticles(@Path("page") int page);


    /**
     * 收藏文章
     *
     * @param id
     * @return
     */
    @POST("/lg/collect/{id}/json")
    Observable<DataResponse> collectArticle(@Path("id") int id);



    /**
     * 取消收藏
     *
     * @param id
     * @param originId http://www.wanandroid.com/lg/uncollect_originId/2333/json
     * @return
     */
    @POST("/lg/uncollect_originId/{id}/json")
    Observable<DataResponse> removeCollectArticle(@Path("id") int id, @Query("originId") int originId);
}
