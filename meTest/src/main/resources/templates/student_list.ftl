<!DOCTYPE html>
<html>
<head>
    <title>学生名单</title>
</head>
<body>
<h1>${classTitle}</h1>
<h2>任课老师: ${teacherName}</h2>
<table>
    <tr>
        <th>姓名</th>
        <th>性别</th>
        <th>年龄</th>
    </tr>
    <#list students as student>
        <tr>
            <td>${student.name}</td>
            <td>${student.gender}</td>
            <td>${student.age}</td>
        </tr>
    </#list>
</table>
</body>
</html>