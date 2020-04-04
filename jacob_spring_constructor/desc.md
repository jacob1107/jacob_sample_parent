127.0.0.1:8080/constructor

装配分为三种：byName, byType, constructor。

- byName就是会将与属性的名字一样的bean进行装配。
- byType就是将同属性一样类型的bean进行装配。
- constructor就是通过构造器来将类型与参数相同的bean进行装配



1. ### byType的 @Autowired（spring提供的），@Inject（java ee提供）

2. ### byName的 @Qualifier（spring提供的），@Named（java ee提供），@Resource（java ee提供）

3. ### constructor 的@Data @RequiredArgsConstructor @AllArgsConstructor