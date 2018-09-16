## 授权
使用`meereen-authorization-api`提供的监听器,类`UserOnSignIn`监听用户授权事件`AuthorizationSuccessEvent`
当用户完成授权(授权方式可自行实现或者使用框架默认的授权方式,主要触发该事件即可).授权通过后会触发该事件.流程如下

1. 完成授权,触发`AuthorizationSuccessEvent`
2. `UserOnSignIn` 收到`AuthorizationSuccessEvent`事件,获取参数`token_type`(默认为`sessionId`),以及授权信息
3. 根据`token_type` 生成token.
4. 将token和授权信息中的userId注册到`UserTokenManager`
5. 将token返回给授权接口