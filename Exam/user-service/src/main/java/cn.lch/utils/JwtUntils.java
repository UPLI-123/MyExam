package cn.lch.utils;


import io.jsonwebtoken.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

// 对jwt 进行的处理 ，报告生成有效凭证
public class JwtUntils {
    /**
     * @param username: 用户名
    	 * @param issuser: 发行版本号
    	 * @param subjecet:  密码
    	 * @param limittime: 有效时间
      * @return String
     * @author  lch
     * @description  对jwt 进行加密
     * @date 2022年4月9日15:35:06
     */
    public static String cretateJwt(String username, String issuser, String subjecet, long limittime){
//         确定所选择的加密算法
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
//        获得当前的系统的时间戳
        long nowTime = System.currentTimeMillis() ;
//        System.out.println(nowTime);

//       将长整形的时间戳 转化为日期格式
        Date now = new Date(nowTime) ;
//        设置加密密钥
        byte [] apiKeySecret = DatatypeConverter.parseBase64Binary("lchzs123456") ;
        Key key = new SecretKeySpec(apiKeySecret, hs256.getJcaName());

//        设置jwt 相关信息
        JwtBuilder builder = Jwts.builder().setId(username)
                .setIssuedAt(now)
                .setSubject(subjecet)
                .setIssuer(issuser)
                .signWith(hs256,key) ;
//        设置凭证的有效时间
        if(limittime >=0 ){
//            exTime 代表有效时间
            long exTime = nowTime + limittime ;
            Date expiration = new Date(exTime) ;
            builder.setExpiration(expiration) ;
        }
//     创建一个 加密后的 jwt
        return builder.compact() ;
    }
    /**
     * @param jwt: jwt消息
     * @return Claims
     * @author lch
     * @description 对加密的jwt进行解密
     * @date  2022年4月9日15:36:59
     */
    public static Claims parseJwt(String jwt){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary("lchzs123456"))
                    .parseClaimsJws(jwt)
                    .getBody();
//            测试用例
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
            return claims ;
        }catch (ExpiredJwtException e){
            return null ;
        }
    }
    /**
     * @param expiration: 当前日期
      * @return boolean
     * @author lch
     * @description 判断是否在有效期内
     * @date 2022年4月9日15:45:10
     */
    public static boolean isTokenExpired(Date expiration){
        return expiration.before(new Date()) ;
    }
//     用来测试 书写的功能是否正常
    public static void main(String[] args) {
        String jwt = cretateJwt("123", "1.0", "456", 1000*60*60*24*7);
        System.out.println(jwt);
//        解密
        parseJwt(jwt) ;
    }
}
