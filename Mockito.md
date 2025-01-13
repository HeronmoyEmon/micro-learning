## **How to Mock**

1. `Mockito.mock(ClassName.class)`
    returns a subclass object of the provided class which can be passed into the constructor of the `CUT` (class under test) 
2. `@Mock` annotation over an instance we want to Mock. 
    
    ```java
    @BeforeEach 
    void SetUp() {
        MockitoAnnotations.openMocks(this);
        this.cut = new CUT(mockInstance1, mockInstance2);
    }
    ```
    Mockito instantiates all the instances with @Mock annotations in the Test Class. 
    
    ```java
    @InjectMocks
    CUT 
    ```
    
    by using `@InjectMocks` we can get rid of the initialization of the `CUT` in `SetUp` method. Mockito will instantiate the `CUT` for us. 

3. least line of codes: 

    `@ExtendWith(MockitoExtension.class)` 
    use this annotation on the Test Class. and use `@Mock` and `@InjectMocks` on the required instance variables. 
    This extension takes care of instantiating and injecting to our test class.
    
    Use `@RunWith(MockitoJUnitRunner.class)` for `JUNIT` older versions. 

    
---
    
    
## **How to Stub with Mockito** 

the default behaviour is to return null by Mockito on invocation of a mocked class method if not stubbed. 

- object -> null
- boolean -> false 
- integer & long -> 0
- double -> 0.0 
- List -> []

```java
@Test
void basicStubbing() {
    Mockito.when(bannedUsersClient.isBanned("duke", new Address())).thenReturn(true); 
    System.out.println(bannedUsersClient.isBanned("duke", new Address())); >> 
}
```


It throws `UnnecessaryStubbingsException` if we don't use the stub. 

we can configure the strictness.

``` java
@MockitoSettings(strictness = Strictness.LENIENT/WARN/STRICT_STUBS) 
```

still it returns `false`. Because `new Address()` instantiates a new object which are not identical though **"duke"** is equal. We can override the defauls equals method of Address class to return true comparing the fields on the Address instances. 


---

```java
ArgumentMatchers.any(Address.class) 
```

if we use this for all parameters. 

```java
Mockito.when(
    bannedUsersClient.isBanned(ArgumentMatchers.eq("duke"), ArgumentMatchers.any(Address.class))
    )
.thenReturn(true);
```
---

```java
Mockito.when(
    bannedUsersClient.isBanned(ArgumentMatchers.anyString(), ArgumentMatchers.isNull())
    )
.thenReturn(true);
```

---



```java
Mokito.when(
    bannedUsersClient.isBanned(ArgumentMatchers.argThat(s -> s.length() <= 3), ArgumentMatchers.isNull())
    )
.thenReturn(false);
```

**Note**: If there are multiple stubbings that satisfy the arguments, then the **last** stubbing will override. 

---

## **Throw exception**

```java
Mockito.when(
    bannedUsersClient.isBanned(eq("duke"), any())
    )
.thenThrow(new RuntimeException("Remote System is down!"));
```

Make the test pass: 
```java
assertThrows(RuntimeException.class, 
() -> System.out.println(bannedUsersClient.isBanned("duke", new Address())));
```

--- 

## **Using .thenAnswer() instead of .thenReturn()**

Instructing the Mock to call the Real Method even though the class instance is Mocked using @Mock annotation.

```java
@Test
void basicStubbingUsageCallRealMethod() {
    when(bannedUsersClient.isBanned(eq("duke"), any(Address.class)))
    .thenCallRealMethod();
}
```

```java
@Test
void basicStubbingUsageCallRealMethod() {
    when(bannedUsersClient.isBanned(eq("duke"), any(Address.class))).thenAnswer(invocation -> {
    String userName = invocation.getArgument(0);
    Address address = invocation.getArgument(1);
    return userName.contains("d") && address.getCity().contains("d");
    });
}
```

**Example 2:** 

```java
when(userRepository.save(any(User.class)))
.thenAnswer(invocation -> {
    User user = invocation.getArgument(0);
    user.setId(42L);
    return user;
    });
```

```java 
when(userRepository.save(argThat(user -> user.getUserName().contains("d"))))
.thenAnswer(invocation -> {
    User user = invocation.getArgument(0);
    user.setId(42L);
    return user;
    });
```
    
this is the same as follows: 

```java
User returnedUser = new User();
returnedUser.setId(42L);

when(userRepository.save(any(User.class)))
.thenReturn(returnedUser);
```
---

