package com.louddt.tag.controller;

import com.alibaba.fastjson.JSON;
import com.louddt.tag.utils.AESCrptography;
import com.louddt.tag.utils.ResponseWrapper;
import com.louddt.tag.utils.StringUtil;
import com.louddt.tag.utils.SymbolConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Api(tags = {} , description = "标志系统公共API")
@RestController
@RequestMapping("common")
public class CommonController extends BaseController{

    @ApiOperation(value = "登录记录",notes = "对接其他系统，获取其他系统登录标识的接口")
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ResponseWrapper<String> loginFromIntgrateSystem(@ApiParam(value = "校验码") @RequestParam("loginKey") String loginKey){

        if(StringUtil.isEmptyOrNull(loginKey)){
            return error("登录校验码不能为空");
        }

        try{
            String decode = AESCrptography.getRealWord(loginKey);
            List<String> kv = Arrays.asList(decode.split(SymbolConstants.SPACE));
            if(kv == null || kv.size() < 2){
                return error("登录失败，校验码错误");
            }
            boolean flag = false;
            if(tokens.size() == 0){
                tokens.put(kv.get(0),kv.get(1));
            }else{
                for (String key : tokens.keySet()) {
                    String val = tokens.get(key);
                    if(val.equals(kv.get(1))){
                        flag = true;
                        tokens.remove(key);
                        tokens.put(kv.get(0),kv.get(1));
                        break;
                    }

                }
                if(!flag){
                    tokens.put(kv.get(0),kv.get(1));
                }
            }
            log.info("已登录信息：" + JSON.toJSONString(tokens));
            String token = kv.get(0);
            return success("成功",token);
        }catch (Exception e){
            e.printStackTrace();
            log.error("校验码解析失败",e);
            return error("登录失败，校验码错误");
        }
    }
}
