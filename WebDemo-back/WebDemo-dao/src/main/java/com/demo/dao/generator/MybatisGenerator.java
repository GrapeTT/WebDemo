package com.demo.dao.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <p>
 * 代码生成器
 * </p>
 *
 * @author tao
 * @since 2021-08-03
 */
public class MybatisGenerator {
    
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
    
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        // 作者
        gc.setAuthor("tao");
        gc.setOpen(false);
        // 去掉接口的首字母I
        gc.setServiceName("%sService");
        // 去掉接口的首字母I
        gc.setServiceImplName("%sServiceImpl");
        // 主键策略
        gc.setIdType(IdType.AUTO);
        // 定义生成的实体类中的日期类型
        gc.setDateType(DateType.ONLY_DATE);
        // 是否覆盖已有文件
        gc.setFileOverride(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);
        
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/demo?characterEncoding=utf8&serverTimezone=UTC");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("666666");
        mpg.setDataSource(dsc);
        
        // 包配置
        PackageConfig pc = new PackageConfig();
        // 包名
        pc.setModuleName("demo");
        pc.setParent("com");
        mpg.setPackageInfo(pc);
        
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        
        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
         String templatePath = "/templates/mapper.xml.vm";
        
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
    /*
    cfg.setFileCreate(new IFileCreate() {
        @Override
        public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
            // 判断自定义文件夹是否需要创建
            checkDir("调用默认方法创建的目录，自定义目录用");
            if (fileType == FileType.MAPPER) {
                // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                return !new File(filePath).exists();
            }
            // 允许生成模板文件
            return true;
        }
    });
    */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();
        
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
        
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 父类实体，没有就不用设置
        strategy.setSuperEntityClass("com.demo.dao.base.BaseEntity");
        //【实体】是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setRestControllerStyle(true);
        // 父类控制器,没有就不用设置
        strategy.setSuperControllerClass("com.demo.web.base.BaseController");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
        // 表名，多个英文逗号分割
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        // URL中驼峰转连字符  addProduct add-product
        strategy.setControllerMappingHyphenStyle(false);
        //去掉布尔值的 is_ 前缀
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }
}
