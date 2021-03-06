package com.donwae.market;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis逆向工程main函数
 * @auther Jeremy
 * 2018/11/12 下午1:48
 */
public class GenMain {
    public static void main(String[] args) {
        List<String> warnings = new ArrayList<String>();
        //如果这里出现空指针，直接写绝对路径即可。
        String genCfg = "/generatorConfig.xml";
        File configFile = new File(GenMain.class.getResource(genCfg).getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        }
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = null;
        try {
            if (config != null) {
                myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        try {
            if (myBatisGenerator != null) {
                myBatisGenerator.generate(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
