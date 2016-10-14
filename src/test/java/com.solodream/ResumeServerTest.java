package com.solodream;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.common.HttpUtils;
import com.solodream.spring.vertx.req.BaseReq;
import com.solodream.spring.vertx.req.client.UserCommonRequestParam;
import com.solodream.spring.vertx.req.client.UserProfileRequestParam;
import com.solodream.spring.vertx.req.client.UserResumeRequestParam;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by young on 16/10/10.
 */
public class ResumeServerTest {
    @Test
    public void testResume() throws Exception {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        BaseReq<UserResumeRequestParam> request = new BaseReq<UserResumeRequestParam>();
        request.setToken("403b87da81e25c5e9cf8b091481464bb");
        UserResumeRequestParam param = new UserResumeRequestParam();

        param.setUserId(1);
        param.setCompany1("华为");
        param.setDescription1("java初级工程师,负责手机阅读客户端");
        param.setFunction1("java工程师");
        param.setWorkTime1("2009-10-01");
        param.setIndustry1("电信行业");

        param.setCompany2("医惠科技");
        param.setDescription2("药品o2o");
        param.setFunction2("部门经理");
        param.setWorkTime2("2011-10-01");
        param.setIndustry2("医疗行业");


        param.setEducation1("余杭高级中学");
        param.setEducationTime1("2005-09-01");
        param.setEducationMajor1("理科");

        param.setEducation2("杭州电子科技大学");
        param.setEducationTime2("2009-09-01");
        param.setEducationMajor2("信息管理与信息系统");


        param.setCredential1("PMP");
        param.setCredentialDesc1("项目管理,经过ppp");

        param.setCredential2("本科毕业证书");
        param.setCredentialDesc2("毕业证书,拿到了");
        param.setIsDefault("1");

        request.setParam(param);
        String parameter = JSON.toJSONString(request);
        System.out.println(parameter);

        String tokenPrefix = parameter.split("\"token\":\"")[1];

        System.out.println(HttpUtils.post("http://127.0.0.1:18080/resume", headers, parameter));
    }


    @Test
    public void testResumeDetail() throws Exception {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        BaseReq<UserCommonRequestParam> request = new BaseReq<UserCommonRequestParam>();
        request.setToken("403b87da81e25c5e9cf8b091481464bb");
        UserCommonRequestParam param = new UserCommonRequestParam();
        param.setUserId(1);
        param.setResumeId(1);
        param.setUserMobile("13857175491");
        param.setUserName("陈洋");
        request.setParam(param);
        String parameter = JSON.toJSONString(request);
        System.out.println(parameter);

        String tokenPrefix = parameter.split("\"token\":\"")[1];

        System.out.println(HttpUtils.post("http://127.0.0.1:18080/resume/detail", headers, parameter));
    }
}
