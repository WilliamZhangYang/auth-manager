package com.hdnav.util;

import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class TokenUtil {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 权限认证
     * @param token
     * @param timestamp
     * @param signature
     * @return
     */
    public boolean verifyAuth(String token,String timestamp,String signature){
        //1.redis中获取token进行验证
        if (!checkToken(token)){
            return false;
        }
        //2.从redis中拿出用户的appid,secret与timestamp进行md5加密
        String key = getKeyByToken(token);
        if (StringUtil.isNullOrEmpty(key)){
            return false;
        }
        //3.用客户端传来的signature与第二步比对进行验签
        if (!checkSignature(key,timestamp,signature)){
            return false;
        }
        return true;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public boolean checkToken(String token){
        //验证key是否存在
        if (redisUtil.hasKey(token)){
            //验证key是否过期
            if (redisUtil.getExpire(token)<0){
                return false;
            }
        }else {
            //TODO 查询数据库中的token
            return false;
        }
        return true;
    }

    /**
     * 通过token获取用户key
     * @param token
     * @return
     */
    public String getKeyByToken(String token){
        String key = redisUtil.get(token).toString();
        return key;
    }

    /**
     * 签名认证
     * @param key
     * @param timestamp
     * @param signature
     * @return
     */
    public boolean checkSignature(String key,String timestamp,String signature){
        //拼接签名
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(timestamp);
        stringBuffer.append("_");
        stringBuffer.append(key);
        String md5Signature = DigestUtils.md5DigestAsHex(stringBuffer.toString().getBytes());
        //判断签名是否相等
        if (!signature.equals(md5Signature)){
            return false;
        }
        return true;
    }

    /**
     * 生成md5加密token
     * @param userId
     * @return
     */
    public String createMD5Token(String userId){
        //md5加密
        String token = DigestUtils.md5DigestAsHex(userId.getBytes());
        return token;
    }
}
