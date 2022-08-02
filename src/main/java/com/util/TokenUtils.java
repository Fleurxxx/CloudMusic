package com.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.entity.VerifyUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;

/**

 * 生成Token
 */
public class TokenUtils {

    /**
     * 过期时间为一天
     * TODO 正式上线更换为60分钟
     */
    private static final long EXPIRE_TIME = 60* 60 * 1000;

    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "189m2JIXqAWz8SM62zesfg==";
    public static final Map<String,Object> TOKEN_USER = new HashMap<>();

    /**
     * 生成签名
     * 默认30分钟失效
     * 不能解密参数
     * @param verifyUser
     * @return
     */
    public static String getTokenGetId(VerifyUser verifyUser) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token = "";
        try {
            //私钥及加密算法
            Algorithm algorithm = null;
            algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头信息
            HashMap<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //附带username和userID生成签名
            token = JWT.create().withHeader(header)
                    .withAudience(String.valueOf(verifyUser.getUserid())) // 签发对象
                    .withClaim("username", verifyUser.getUsername())// 载荷
                    .withClaim("password", verifyUser.getPassword())
//                    .withAudience(verifyUser.getUsername())
//                    .withAudience(verifyUser.getPassword())
                    .withExpiresAt(date)
                    .sign(algorithm);
            TOKEN_USER.put(token,verifyUser);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 验证token
     * 正确返回true  错误返回false
     * @param token
     * @return
     */
    public static boolean getIsToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    /**
     * 解密 获取id
     *
     * @param token
     * @return
     */
    public static String getTokenId(String token) {
        try {
            return JWT.decode(token).getAudience().get(0);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 获取 verifyUser
     * @param token
     * @return
     */
    public static VerifyUser getVer(String token){
        return (VerifyUser) TOKEN_USER.get(token);
    }


    /**
     * 通过载荷名称获取载荷值
     *
     * @param token     令牌
     * @param claimName 载荷名称
     * @return 载荷值
     */
    public static Claim getClainByName(String token, String claimName) {
        return JWT.decode(token).getClaim(claimName);
    }


    /**
     * 生成 Token
     *
     * @param id      ID
     * @param data    自定义参数
     * @param endTiem 具体到期时间
     * @return 返回 token
     */
    public static String getCreateJwt(String id, Map<String, String> data, Date endTiem) {
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(new Date())//时间
                .claim("code", data)//添加自定义的数据
                .setSubject("丽诺尔")
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                .setExpiration(new Date(endTiem.getTime()));//方法用于设置过期时间
        return builder.compact();
    }

    /**
     * 解密 Token
     *
     * @param token
     */
    public static Map<String, Object> getpareJwt(String token) {

        try {
            Claims claims = Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
            //System.out.println("id:" + claims.getId());
            //System.out.println("subject:" + claims.getSubject());
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy‐MM‐dd hh:mm:ss");
            //System.out.println("签发时间:" + sdf.format(claims.getIssuedAt()));
            //System.out.println("过期时间:" + sdf.format(claims.getExpiration()));
            //System.out.println("当前时间:" + sdf.format(new Date()));
            //System.out.println("自定义数据:" + claims.get("code"));
            Map<String, Object> data = (Map<String, Object>) claims.get("code");
            data.put("id", claims.getId());
            data.put("subject", claims.getSubject());
            data.put("signTime", claims.getIssuedAt());
            data.put("endTime", claims.getExpiration());
            return data;
        } catch (Exception e) {
            return null;
        }


    }


    @SneakyThrows
    public static void main(String[] res) {

        VerifyUser verifyUser = new VerifyUser(22222,"admin", "123456");
        String token = getTokenGetId(verifyUser);
        System.out.println("正确返回true反之返回false:\n"+getIsToken(token));
//         token=getTokenGetId("admin", "1");
        // token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxIiwiZXhwIjoxNjEwNDMwOTQ5fQ.lg2ZzR8zl7sr6zn-YlWDtUFwl9YxY90J6LZIr3fwOcQ";
        // token ="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjMiLCJpYXQiOjE2MjU0NTIxODAsImNvZGUiOnt9LCJzdWIiOiLkuL3or7rlsJQiLCJleHAiOjE2MjUxMjE5OTl9.IEVlj9Gwer5PEJTTAOFs-hJzjM3LZsGqPIesfAOSVvw";
        //休眠5s
        ///TimeUnit.SECONDS.sleep(10);
        System.out.println("验证并返回ID:\n"+getTokenId(token));

//        Map map = new HashMap();
//        map.put("name", "xijue");
//        map.put("pass", "12346");
////        token = getCreateJwt("1", map, DateUtil.getStrToDate("2221-01-10 14:59:00"));
////        System.out.println(token);
////        TimeUnit.SECONDS.sleep(5);
//        token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjMiLCJpYXQiOjE2MjU0NTIyODQsImNvZGUiOnt9LCJzdWIiOiLkuL3or7rlsJQiLCJleHAiOjE2MjUxMjE5OTl9.rOtWDZJpQOIgOvbXRcYWy8DHWa-dhSlWxyHZbCSchXs";
//        System.out.println("返回Map:\n"+getpareJwt(token));
    }

}