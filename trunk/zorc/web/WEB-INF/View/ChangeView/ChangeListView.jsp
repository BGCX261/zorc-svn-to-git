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
        <title>Изменения</title>
    </head>
    <body>

        <zorc:top number="3"/>

        <div class="row-fluid">
            <div class="well span10 offset1">
                <div class="span10 input-prepend input-append">
                    <form id="searchForm" method="POST" action="main?action=change.list">
                        <fieldset>
                            <select class="span3" id="typeSearch" name="typeSearch">
                                <option value="by_code">По дате</option>
                            </select>

                            <input class="span6" name="searchText" id="searchText" type="text" value="${searchText}">

                            <div class="btn-group">
                                <button class="btn" type="submit">
                                    Найти
                                </button>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>

        <div class="row-fluid">
            <div class="span10 offset1">
                <div class="scrolltable">
                    <table class="table table-hover table-bordered table-striped table-condensed">
                        <thead>
                            <tr>
                                <th>Дата изменения</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="change" items="${changeList}">
                                <c:url var="changeURL" value="main">
                                    <c:param name="action" value="plan.list"/>
                                    <c:param name="id" value="${change.id}"/>
                                </c:url>

                                <tr onclick="location.href='${changeURL}'"
                                    onMouseOver="this.className='info';"
                                    onMouseOut="this.className='';">
                                    <td>
                                        <c:if test="${empty change.date}">
                                            <i>не зафиксировано</i>
                                        </c:if>
                                        <c:if test="${!empty change.date}">
                                            <fmt:formatDate pattern="dd.MM.yyyy" value="${change.date}" var="changeData"/>
                                            <c:out value="${changeData}"/>
                                        </c:if>
                                    </td>  
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </body>
</html>
