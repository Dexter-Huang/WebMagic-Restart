## Github/Stackoverflow 网站数据可视化平台
- 该项目基于MVC架构模式进行实现。该项目通过Github，StackOverflow开放的相关API，实现每日定时爬取、更新数据, 并以可视化的方式展示相关爬取的数据如热门repo排行榜、不同语言(Java,Python等)领域内热门问题、热门回答、热门话题等，帮助不同领域开发人员了解相关行业发展趋势。

- 主要技术栈：
      SpringBoot+Mybatisplus+WebMagic+Quartz+MySQL+LayUI(html/css)+Echarts

- 责任描述:
（1）基于WebMagic 多线程、每日定时爬取Github/StackOverflow 网站的数据，实现网站数据的定时更新
（2）利用Mybatis-plus自动生成相关业务逻辑，结合Springboot快速搭建网站平台
（3）根据不同语言、不同领域，设计合理的数据库表，方便数据可视化展示
