# 将每天做的工作记录于此
先说感想，说实话拿到demand后，首先想到的是在SpringBoot中有Banner功能的，所以查看了SpringBoot源码发现确实是这个关系,
可以参考其他人的做法然后理解消化变为自己的东西


## 2018-04-24
1. 将代码传到git上，做管理，方便维护
2. 还是测试先行，引入log4j，junit

## 2018-04-26
1. 看了SpringBoot中ImageBanner的实现，是要比这项目要好一些，Spring中使用了ImageReader，获取到了每个Image Frame，再将每个Frame进行resize和然后转为banner打印
Spring实现比直接使用BufferImage直接读取图片效率要高
2. 实现有些复杂，看看是否可以简化一下，很多参数对于完成这个测试题，可以不去考虑

## 2018-04-27
1. 我将它简化，直接使用ImageIO读取出BufferImage，然后直接调用SpringBoot中的printBanner
2. 可以输出为文件了，明天再补上两张UML图说明一下就可以了

