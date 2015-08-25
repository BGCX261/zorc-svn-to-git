<%@taglib tagdir="/WEB-INF/tags" prefix="zorc" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.css" type="text/css" />
        <link rel="stylesheet" href="css/bootstrap-responsive.css" type="text/css" />
        <link rel="stylesheet" href="css/styles.css" type="text/css" />
        <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <title>План</title>
    </head>
    <body>

        <zorc:top number="1"/>

        <div class="row-fluid">
            <div class="well span10 offset1">
                <div class="span8 input-prepend input-append">
                    <form id="searchForm" method="POST" action="main?action=plan.list">
                        <fieldset>
                            <select class="span3" id="typeSearch" name="typeSearch">
                                <c:if test="${typeSearch eq 'by_date'}">
                                    <option value="by_date">По дате</option>
                                    <option value="by_name">По имени</option>
                                </c:if>
                                <c:if test="${typeSearch ne 'by_date'}">
                                    <option value="by_name">По имени</option>
                                    <option value="by_date">По дате</option>
                                </c:if>
                            </select>


                            <input class="span6" name="searchText" id="searchText" type="text" value="${searchText}">

                            <div class="btn-group">
                                <button class="btn">
                                    Найти
                                </button>
                            </div>

                        </fieldset>
                    </form>
                </div>
                <a class="btn btn-success" href="main?action=change.fix"> Зафиксировать </a>
                <!--<a class="btn btn-success pull-right" href="main?action=plan.send"> Отправить отчёт </a> -->
            </div>
        </div>

        <div class="row-fluid prev-next-buttons">
            <div class="span10 offset1">
                <div class="span1 left-button">
                    <c:if test="${!empty prevId}">
                        <c:url var="planURL" value="main">
                            <c:param name="action" value="plan.list"/>
                            <c:param name="id" value="${prevId}"/>
                        </c:url>
                        <a href="${planURL}" class="btn">
                            &lt;
                        </a>                            
                    </c:if>
                </div>
                <div class="span10 center-info">
                    <c:if test="${empty change.date}">
                        <p>Текущий список</p>
                    </c:if>
                    <c:if test="${!empty change.date}">
                        <fmt:formatDate pattern="dd.MM.yyyy" value="${change.date}" var="changeData"/>
                        <p>Список на <c:out value="${changeData}"/></p>
                    </c:if>
                </div>
                <div class="span1 right-button">
                    <c:if test="${!empty nextId}">
                        <c:url var="planURL" value="main">
                            <c:param name="action" value="plan.list"/>
                            <c:param name="id" value="${nextId}"/>
                        </c:url>
                        <a href="${planURL}" class="btn">
                            &gt;
                        </a>                            
                    </c:if>
                </div>
            </div>  
        </div>

        <div class="row-fluid">
            <div class="span10 offset1">
                <div class="scrolltable">
                    <table class="table table-hover table-bordered table-striped table-condensed ">
                        <thead>
                            <tr>
                                <th>№</th>
                                <th>Код продукта</th>
                                <th>Название продукта</th>
                                <th>Название</th>
                                <th>Единицы измерения</th>
                                <th>Цена</th>
                                <th>Количество</th>
                                <th>Начало</th>
                                <th>Конец</th>
                                <th>Комментарий</th>
                            </tr>
                        </thead>

                        <tbody>
                            <jsp:useBean id="date" class="java.util.Date"/>

                            <c:forEach var="plan" items="${notChangedPlans}">
                                <c:url var="planURL" value="main">
                                    <c:param name="action" value="plan.edit"/>
                                    <c:param name="id" value="${plan.id}"/>
                                </c:url>

                                <tr id="plan${plan.id}" 
                                    <c:if test="${empty nextId}">
                                        onclick="location.href='${planURL}'"
                                    </c:if>
                                >
                                    <td><c:out value="${plan.position}"/></td>
                                    <td><c:out value="${plan.getProduct().getCode()}"/></td>
                                    <td><c:out value="${plan.getProduct().name}"/></td>
                                    <td><c:out value="${plan.name}"/></td>
                                    <td><c:out value="${plan.unit.getSymbol()}"/></td>
                                    <td><c:out value="${plan.price}"/></td>
                                    <td><c:out value="${plan.amount}"/></td>
                                    <td>
                                        <fmt:formatDate pattern="dd.MM.yyyy" value="${plan.begin}" var="beginDate"/>
                                        ${beginDate}
                                    </td>
                                    <td>
                                        <fmt:formatDate pattern="dd.MM.yyyy" value="${plan.end}" var="endDate"/>
                                        ${endDate}
                                    </td>
                                    <td><c:out value="${plan.comment}"/></td>  
                                </tr>
                            </c:forEach>

                            <c:forEach var="plan" items="${addedPlans}">
                                <c:url var="planURL" value="main">
                                    <c:param name="action" value="plan.edit"/>
                                    <c:param name="id" value="${plan.id}"/>
                                </c:url>

                                <tr id="plan${plan.id}" class="success"
                                    <c:if test="${empty nextId}">
                                        onclick="location.href='${planURL}'"
                                    </c:if>
                                >
                                    <td><c:out value="${plan.position}"/></td>
                                    <td><c:out value="${plan.getProduct().getCode()}"/></td>
                                    <td><c:out value="${plan.getProduct().name}"/></td>
                                    <td><c:out value="${plan.name}"/></td>
                                    <td><c:out value="${plan.unit.getSymbol()}"/></td>
                                    <td><c:out value="${plan.price}"/></td>
                                    <td><c:out value="${plan.amount}"/></td>
                                    <td>
                                        <fmt:formatDate pattern="dd.MM.yyyy" value="${plan.begin}" var="beginDate"/>
                                        ${beginDate}
                                    </td>
                                    <td>
                                        <fmt:formatDate pattern="dd.MM.yyyy" value="${plan.end}" var="endDate"/>
                                        ${endDate}
                                    </td>
                                    <td><c:out value="${plan.comment}"/></td>  
                                </tr>
                            </c:forEach>

                            <c:forEach var="plan" items="${modifiedPlans}">
                                <c:url var="planURL" value="main">
                                    <c:param name="action" value="plan.edit"/>
                                    <c:param name="id" value="${plan.id}"/>
                                </c:url>

                                <tr id="plan${plan.id}" class="info"
                                    <c:if test="${empty nextId}">
                                        onclick="location.href='${planURL}'"
                                    </c:if>
                                >
                                    <td><c:out value="${plan.position}"/></td>
                                    <td><c:out value="${plan.getProduct().getCode()}"/></td>
                                    <td><c:out value="${plan.getProduct().name}"/></td>
                                    <td><c:out value="${plan.name}"/></td>
                                    <td><c:out value="${plan.unit.getSymbol()}"/></td>
                                    <td><c:out value="${plan.price}"/></td>
                                    <td><c:out value="${plan.amount}"/></td>
                                    <td>
                                        <fmt:formatDate pattern="dd.MM.yyyy" value="${plan.begin}" var="beginDate"/>
                                        ${beginDate}
                                    </td>
                                    <td>
                                        <fmt:formatDate pattern="dd.MM.yyyy" value="${plan.end}" var="endDate"/>
                                        ${endDate}
                                    </td>
                                    <td><c:out value="${plan.comment}"/></td>  
                                </tr>
                            </c:forEach>

                            <c:forEach var="plan" items="${removedPlans}">                            
                                <tr id="plan${plan.id}" class="error">
                                    <td><c:out value="${plan.position}"/></td>
                                    <td><c:out value="${plan.getProduct().getCode()}"/></td>
                                    <td><c:out value="${plan.getProduct().name}"/></td>
                                    <td><c:out value="${plan.name}"/></td>
                                    <td><c:out value="${plan.unit.getSymbol()}"/></td>
                                    <td><c:out value="${plan.price}"/></td>
                                    <td><c:out value="${plan.amount}"/></td>
                                    <td>
                                        <fmt:formatDate pattern="dd.MM.yyyy" value="${plan.begin}" var="beginDate"/>
                                        ${beginDate}
                                    </td>
                                    <td>
                                        <fmt:formatDate pattern="dd.MM.yyyy" value="${plan.end}" var="endDate"/>
                                        ${endDate}
                                    </td>
                                    <td><c:out value="${plan.comment}"/></td>  
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <br>
                <a class="btn btn-primary pull-left" href="main?action=plan.create"> Добавить запись </a>

            </div>
        </div>

    </body>
</html>
