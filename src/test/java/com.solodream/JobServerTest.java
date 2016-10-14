package com.solodream;

import com.alibaba.fastjson.JSON;
import com.solodream.spring.vertx.common.HttpUtils;
import com.solodream.spring.vertx.req.BaseReq;
import com.solodream.spring.vertx.req.client.ApplyJobRequestParam;
import com.solodream.spring.vertx.req.client.JobRequestParam;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by young on 16/10/10.
 */
public class JobServerTest {
    @Test
    public void testJobs() throws Exception {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        BaseReq<JobRequestParam> request = new BaseReq<JobRequestParam>();
        request.setToken("403b87da81e25c5e9cf8b091481464bb");
        JobRequestParam param = new JobRequestParam();
        param.setKeyword("java工程师");
        param.setOffset(0);
        param.setLength(20);
        request.setParam(param);
        String parameter = JSON.toJSONString(request);
        System.out.println(parameter);

//        String tokenPrefix = parameter.split("\"token\":\"")[1];

//        System.out.println(HttpUtils.post("http://127.0.0.1:18080/user/job", headers, parameter));
    }
    @Test
    public void testCollectJob() throws Exception {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        BaseReq<ApplyJobRequestParam> request = new BaseReq<ApplyJobRequestParam>();
        request.setToken("403b87da81e25c5e9cf8b091481464bb");
        ApplyJobRequestParam param = new ApplyJobRequestParam();
        param.setCompanyName("阿里巴巴");
        param.setJobName("技术专家");
        param.setJobId(1);
        param.setUserId(1);
        param.setStyle("0");

        request.setParam(param);
        String parameter = JSON.toJSONString(request);
        System.out.println(parameter);

        String tokenPrefix = parameter.split("\"token\":\"")[1];

        Map<String, String> params=new HashMap<>();



        System.out.println(HttpUtils.get("http://127.0.0.1:18080/collect/",params));
    }

//    @Test
//    public void testApplyJob() throws Exception {
//        List<Header> headers = new ArrayList<Header>();
//        headers.add(new BasicHeader("Content-Type", "application/json"));
//        BaseReq<ApplyJobRequestParam> request = new BaseReq<ApplyJobRequestParam>();
//        request.setToken("403b87da81e25c5e9cf8b091481464bb");
//        ApplyJobRequestParam param = new ApplyJobRequestParam();
//        param.setCompanyName("阿里巴巴");
//        param.setJobName("技术专家");
//        param.setJobId(1);
//        param.setUserId(1);
//        param.setStyle("1");
//
//        request.setParam(param);
//        String parameter = JSON.toJSONString(request);
//        System.out.println(parameter);
//
////        String tokenPrefix = parameter.split("\"token\":\"")[1];
//
//        System.out.println(HttpUtils.post("http://127.0.0.1:18080/collect", headers, parameter));
//    }
}
