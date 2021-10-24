package cn.krl.visiteducationbackend.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

/**
 * @description jwt工具 用于token的生成和验证 实际上与shiro的seeionId功能重叠 因为后端先用了shiro 前端模板要求token 所有集成了jwt 而且我也不想
 * @author kuang
 * @data 2021/10/24
 */
public class JwtUtil {
    private static String secret = "ILOVEWHU";

    /**
     * 新建一个token
     *
     * @param id 用户id
     * @param name 用户名
     * @return
     */
    public static String createToken(String id, String name, String type) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 1440);
        Date expiresDate = nowTime.getTime();
        return JWT.create()
                // 签发对象
                .withAudience(id)
                // 发行时间
                .withIssuedAt(new Date())
                // 有效时间
                .withExpiresAt(expiresDate)
                // 载荷，管理员姓名和类型
                .withClaim("name", name)
                .withClaim("type", type)
                // 加密  id+String为密钥
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 检验token合法性
     *
     * @param token
     */
    public static void verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            verifier.verify(token);
        } catch (Exception e) {
            // 效验失败
            System.out.println(e);
        }
    }

    /**
     * 获取签发对象
     *
     * @param token
     * @return
     */
    public static String getAudience(String token) {
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
     *
     * @param token
     * @param key
     * @return
     */
    public static Claim getClaimByName(String token, String key) {
        return JWT.decode(token).getClaim(key);
    }
}
