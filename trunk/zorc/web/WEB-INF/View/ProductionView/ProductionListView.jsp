<%@taglib tagdir="/WEB-INF/tags" prefix="zorc" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        <zorc:top number="2"/>

        <div class="row-fluid">
            <div class="well span10 offset1">
                <div class="span10 input-prepend input-append">
                    <form id="searchForm" method="POST" action="main?action=production.list">
                        <fieldset>
                            <select class="span3" id="typeSearch" name="typeSearch">
                                <c:if test="${typeSearch eq 'by_code'}">
                                    <option value="by_code">По коду</option>
                                    <option value="by_name">По имени</option>
                                </c:if>
                                <c:if test="${typeSearch ne 'by_code'}">
                                    <option value="by_name">По имени</option>
                                    <option value="by_code">По коду</option>
                                </c:if>
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
                                <th>Код продукта</th>
                                <th>Название</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="production" items="${productionList}">
                                <tr id="production" onMouseOver="this.className='info';"
                                    onMouseOut="this.className='';">
                                    <td>
                                        <c:out value="${production.code}"/>
                                    </td>
                                    <td>
                                        <c:out value="${production.name}"/>
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
