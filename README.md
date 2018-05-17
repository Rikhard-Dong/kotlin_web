# 使用Kotlin + SpringBoot + JPA 进行web开发极简教程

开始前需要有java基础, SpringBoot基础和kotlin基础

kotlin参考[kotlin中文站的教程](https://www.kotlincn.net/), 相信对于一个Java程序员来说, 半天就能上手了

## 为什么选择Kotlin
Kotlin比起java来说更加简洁, 和java一样是基于JVM的编程语言, 网上关于Kotlin优点缺点的讨论也有很多, 这里就不展开了.

当然, 最主要的原因的是, 暑假实习的公司, 使用Kotlin和SpringBOot进行web开发的, o(╯□╰)o

之前对于kotlin的了解甚少, 只知道在去年的google I/O大会上成了安卓的第一语言, 其他就不了解了.
现在趁暑假前, 先学习一下kotlin.

## 教程开始
### 创建一个SpringBoot工程
首先当然是使用IDEA创建一个SpringBoot工程 
![](http://p7vwhtuac.bkt.clouddn.com/kotlin/%E5%88%9B%E5%BB%BAKotlin+SpringBoot%E5%B7%A5%E7%A8%8B.png)

这里语言选择Kotlin, 使用gradle进行管理, 之后再选择模块的时候只要选择上web, jpa和mysql就可以了

然后修改配置文件, 因为导入了jpa, 所以一定要设置好DataSource, 否则无法启动
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    username: root
    password: ABCabc123#
    url: jdbc:mysql://localhost:3306/db_test?useSSL=false
```

配置完成后可以, 在source目录下可以看到已经创建好了一个Application.kt文件, 用于启动SpringBoot, 对应Java下的Application.java
文件
```kotlin
@SpringBootApplication
class TestApplication

fun main(args: Array<String>) {
    runApplication<TestApplication>(*args)
}
```

### 创建Controller
```kotlin
@RestController
@RequestMapping("/hello")
class HelloController {
    @GetMapping
    fun hello():String {
        return "hello world"
    }
}
```
和java的写法非常像, 可以无缝转换

启动!

使用curl命令进行请求
```
➜  ~ curl "http://localhost:8080/hello"
hello world                                                          
```
简单的请求完成了

### 使用Swagger2生成接口文档
使用Swagger2可以自动生成接口文档和进行接口测试, 极大的方便了后端, 不需要去花很大的功夫获去维护文档

首先试试导入Swagger2
```groovy
    compile group: 'io.springfox', name: 'springfox-swagger2', version: '2.8.0'
    compile group: 'io.springfox', name: 'springfox-swagger-ui', version: '2.8.0'
```
配置Swagger2
```kotlin
@Configuration
@EnableSwagger2
class Swagger2 {

    @Bean
    fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)  // 使用Swagger2
                .apiInfo(apiInfo())                 // 设置接口页面信息
                .select()                           // 返回ApiSelectorBuilder的实例
                .apis(RequestHandlerSelectors.basePackage("io.ride.vote.web"))      // api接口所在的包
                .paths(PathSelectors.any())         
                .build()
    }

    /**
     * 页面信息展示
     */
    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Vote RestFul APIs文档")
                .description("项目API接口文档")
                .contact(Contact("ride", "", "supreDong@gamil.com"))
                .version("0.0.1")
                .build()
    }
}
```
***@Configuration***注解表明这是一个配置类, ***@EnableSwagger2***注解表明启用Swagger2

通过在controller中添加注解来生成api文档
```kotlin
@Api(value = "测试", description = "测试控制器")
@RestController
@RequestMapping("/hello")
class HelloController {

    @GetMapping
    @ApiOperation("你好!世界!", notes = "返回hello world")
    fun hello(): String {
        return "hello world"
    }
}
```
之后打开**http://localhost:8080/swagger-ui.html**可以看到生成的接口信息
![](http://p7vwhtuac.bkt.clouddn.com/kotlin/%E4%BD%BF%E7%94%A8Swagger2%E7%94%9F%E6%88%90api%E6%8E%A5%E5%8F%A3%E6%96%87%E6%A1%A3.png)如图, 在该页面上还以对接口进行测试

### 统一异常处理
和java下的操作是一致的, 只是把java翻译成了kotlin
```kotlin
@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(ApiException::class)
    fun handlerApiException(e: ApiException): ResponseEntity<Result> {
        val result = Result(e.code, e.data)
        return result.ok()

    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handMissingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseEntity<Result> {

        val result = Result(HttpStatus.BAD_REQUEST.value(), e.message)
        return result.ok()
    }

}

class ApiException(val code: ResultCode, val data: HashMap<String, Any>? = null) : RuntimeException(code.msg)
```

### 使用JPA
首先配置JPA:
```yaml
spring:
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
    database: mysql
```
创建data类
```kotlin
@Entity
@Table(name = "t_user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = -1,
        @Column(nullable = false)
        var username: String? = null,
        @Column(nullable = false)
        var password: String? = null,
        @Column(nullable = false)
        var email: String? = null,
        @Column(nullable = true)
        var nickname: String? = null,
        @Column(nullable = false)
        var createTime: Date = Date()
)
```
创建repository类
```kotlin
interface IUserService {
    /**
     * 添加一个用户
     */
    fun addUser(user: User): User

    /**
     * 展示所有用户
     */
    fun listAll(): List<User>

    /**
     * 删除一个用户
     */
    fun deleteUser(id: Long)

}
```
进行单元测试
```kotlin
@RunWith(SpringRunner::class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `find all user test`() {
        println(userRepository.findAll())
    }

    @Test
    fun `add user test`() {
        val user = User(username = "ride", email = "supreDong@gmail.com", password = "123123", nickname = "ride")
        println(userRepository.save(user))
    }

    @Test
    fun `delete user test`() {
        val user = userRepository.findById(1)
        println(user.orElse(null))
        if (user.isPresent)
            userRepository.deleteById(user.get().id)
    }
}
```

在单元测试并且只能在单元测试中(kotlin1.2)可以使用反引号来定义方法

## 总结
使用使用kotlin结合SpringBoot是一种从船新体验, 推荐大家尝试一下