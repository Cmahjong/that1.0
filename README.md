# that1.0

# Activity 插件化思路
- 主要是通过宿主App里面创建一个ProxyActivity,然后创建一个面向接口编程的中间接口类IItentface,然后里面声明activity的所有声明周期，主要是为了骑着桥梁的作用，然后就是proxyActivity执行的所有声明周期都是调用apk里面的activity的声明周期，但是资源问题还是不能访问，怎么访问，我们就直接将这个ProxyActivity的对象传入到apk里面的Activity(DL定义为that),然后在ProxyActivity进行资源合并工作，反射执行AssetManager里面的addAssetPath(String path)，将第三方apk的资源整合在这个里面，然后直接可以通过ProxyActivity来进行资源的访问

-宿主Activity的声明周期还是要执行的，比如onDestroy，如果不执行的话，那么这个Activity永远就不会onDestroy,这样暂用资源,activity占的问题等问题，onCreate,以及onStartCommand等都要执行

- 问题：现在使用v7包的Activity,出现没有Theme的崩溃，暂时还不知道怎么解决
# Service 插件化思路
-基本上的思路和Activity一样，通过宿主app中的service进行分发

-宿主Service的声明周期还是要执行的，比如onDestroy，如果不执行的话，那么这个Service永远就不会onDestroy,这样暂用资源等问题，onCreate,以及onStartCommand等都要执行

- 注意我们loadClass的时候一般是在onStartCommand，如果不得话，那我们就会失去方法onStartCommand的执行也会改变AMS和PT、AT之间的创建以及底层原理的东西，所以我们还是要在onStartCommand执行方法如下：

```java
 @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String dexPath = getFileStreamPath("that.apk").getPath();
        DexClassLoader classLoader = new DexClassLoader(dexPath, getDir("dex", Context.MODE_PRIVATE).getPath(), null, getClassLoader());
        try {
            selfService = (ISelfService) classLoader.loadClass("com.yj.that_plugin.MainService").newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        selfService.setProxyService(this);
        return selfService.onStartCommand(intent, flags, startId);
    }
```

这样有个弊端就是你每次都要加载calssLoader以及newInstance获取这个apk的Service，我们知道这样是很耗性能的，怎么解决的：bigo-那我们创建一个map或者list(具体看业务需要)来进行保存，是不是就要好些了呢

- 其实你还会发现一个问题就是onCreate没有执行，那又怎么办呢，我们手动执行一次（主要是你在onCreate里面执行了一些业务逻辑），

```java
 @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String dexPath = getFileStreamPath("that.apk").getPath();
        DexClassLoader classLoader = new DexClassLoader(dexPath, getDir("dex", Context.MODE_PRIVATE).getPath(), null, getClassLoader());
        try {
            selfService = (ISelfService) classLoader.loadClass("com.yj.that_plugin.MainService").newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        selfService.onCreate();
        selfService.setProxyService(this);
        return selfService.onStartCommand(intent, flags, startId);
    }
```

- 但是我们会发现在bindService会有问题，会导致程序崩溃，为什么呢？我们做处理都是在onStartCommand里面，这个startService是没有问题，但是bindService就有问题了，因为整个生命周期不会走onStartCommand,也就是我们selfService这个面向接口对象编程的代理对象是为null，那怎么处理呢，我们直接在onBind里面做处理就行了呢 代码如下：


```java
 ISelfService selfService;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        String dexPath = getFileStreamPath("that.apk").getPath();
        DexClassLoader classLoader = new DexClassLoader(dexPath, getDir("dex", Context.MODE_PRIVATE).getPath(), null, getClassLoader());
        try {
            selfService = (ISelfService) classLoader.loadClass("com.yj.that_plugin.MainService").newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        selfService.setProxyService(this);
        selfService.onCreate();
        selfService.onBind(intent);
        return null;
    }
```  
    
