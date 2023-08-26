# 编译
{
    mvn clean package -Dmaven.test.skip=true
    mvn clean test
}

# 测试
{
    antlr-run C compilationUnit -tree examples/helloworld.c
}
