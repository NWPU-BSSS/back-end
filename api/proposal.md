# /articleList

## Query参数

| 参数名 | 类型     | 必填 | 说明         |
| ------ | -------- | ---- | ------------ |
| type   | [string] | 是   | 表征id的类型 |
| id     | [number] | 否   |              |

type的取值范围是：

1. recommend，此时id不填
2. user，此时id为用户的id
3. choice，此时id为“编辑精选”的列表id（以后会有这个功能）