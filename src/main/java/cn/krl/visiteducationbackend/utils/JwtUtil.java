package cn.krl.visiteducationbackend.utils;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;


public class JwtUtil {
    private static String secret = "ILOVEWHU";
    
    /**
     * 新建一个token
     * @param id    用户id
     * @param name  用户名
     * @return
     */
    public static String createToken(String id,String name) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,30);
        Date expiresDate = nowTime.getTime();

        return JWT.create()
            //签发对象
            .withAudience(id)
            //发行时间
            .withIssuedAt(new Date())
            //有效时间
            .withExpiresAt(expiresDate)
            //载荷，随便写几个都可以
            .withClaim("name", name)
            //加密  id+String为密钥
            .sign(Algorithm.HMAC256(secret));
    }


    /**
     * 检验token合法性
     * @param token
     */
    public static void verifyToken(String token){
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            verifier.verify(token);
        } catch (Exception e) {
            //效验失败
            System.out.println(e);
        }
    }

    /**
     * 获取签发对象
     * @param token
     * @return
     */
    public static String getAudience(String token){
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            System.out.println(j);
        }
        return audience;
    }


    /**
     * 通过载荷名字获取载荷的值
     * @param token
     * @param key
     * @return
     */
    public static Claim getClaimByName(String token, String key){
        return JWT.decode(token).getClaim(key);
    }
}

