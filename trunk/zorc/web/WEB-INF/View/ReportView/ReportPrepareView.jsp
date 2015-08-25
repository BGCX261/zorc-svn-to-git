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
        <script type="text/javascript" src="js/script.js"></script>
        <title>Отчет</title>
    </head>
    <body>

        
        <zorc:top number="4"/>

        <div class="row-fluid">
            <div class="span10 offset1">
                <div class="scrolltable">
                    <form name="form" method="POST" action="main?action=report.generate">
                        <c:if test="${complete == 'yes'}">
                            <div class="alert alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <p>Отчет был успешно создан!</p>
                            </div>
                        </c:if> 
                        
                    <table name="table" class="table table-hover table-bordered table-striped table-condensed">
                        <thead>
                            <tr>
                                <th>Выберете дату фиксации:</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="change" items="${fixChangeList}">
                                <fmt:formatDate pattern="dd.MM.yyyy" value="${change.date}" var="changeData"/>
                                
                                <tr onclick="fillAndShow(document.form.selectedDate,document.getElementById('${change.id}'))"
                                    onMouseOver="this.className='info';"
                                    onMouseOut="this.className='';">
                                    <td id="${change.id}"><c:out value="${changeData}"/></td>  
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                        <div id="end" class="hidden">
                        <label for="selectedDate">Выбранная дата фиксации: </label>
                        <input name="selectedDate" id="selectedDate" type="text" value=""  required="true"/><br/>
                        <label for="path">Укажите полный путь для отчета (например: С:\out.xls):</label>
                        <input type="text" name="path" required/><br/>
                        <input class="btn btn-success" type="submit" value="Сгенерировать отчет"/>
                        </div>
                    </form>
                    
                </div>
            </div>
        </div>

    </body>
</html>
