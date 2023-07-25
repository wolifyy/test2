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
    <#if type == "Teacher">
    <h1>XXX班级学生成绩名单</h1>
    <#list teachers as teacher>
        <p>${teacher.name} - ${teacher.grade}</p>
    </#list>
    <#elseif type == "Student">
    <#list students as student>
        <tr>
            <td>${student.name}</td>
            <td>${student.gender}</td>
            <td>${student.age}</td>
        </tr>
    </#list>
    </#if>
    <#list students as student>
        <tr>
            <td>${student.name}</td>
            <td>${student.gender}</td>
            <td>${student.age}</td>
        </tr>
    </#list>
    <h2>不及格的同学有:
        <#list students as student>
            ${student.name}<#sep>、</#sep>
        </#list>
    </h2>
</table>
</body>
</html>