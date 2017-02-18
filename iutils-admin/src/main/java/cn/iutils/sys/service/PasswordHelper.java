package cn.iutils.sys.service;

import cn.iutils.common.config.JConfig;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.iutils.sys.entity.User;

/**
 * 密码操作
 *
 * @author cc
 */
@Service
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${shiro.algorithmName}")
    private String algorithmName;
    //    private String algorithmName = JConfig.getConfig("shiro.algorithmName");
    @Value("${shiro.hashIterations}")
    private int hashIterations;
//    private int hashIterations = JConfig.getInteger("shiro.hashIterations");

    public void setRandomNumberGenerator(
            RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    /**
     * 密码加密
     *
     * @param user
     */
    public void encryptPassword(User user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(algorithmName, user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
        user.setPassword(newPassword);
    }

    /**
     * 获取密码
     *
     * @param user
     * @return
     */
    public String getPassword(User user) {
        String password = new SimpleHash(algorithmName, user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
        return password;
    }

}
