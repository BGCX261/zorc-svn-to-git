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

        <link rel="stylesheet" href="css/tcal.css" type="text/css" />
        <link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/themes/smoothness/jquery-ui.css" />

        <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>

        <script type="text/javascript" src="js/tcal.js"></script>
        <script type="text/javascript" src="js/script.js"></script>
        <title>Просмотр записи</title>
    </head>

    <body>

        <zorc:top number="1"/>

        <div class="row-fluid">

            <div class="well span10 offset1">

                <div style="margin-bottom: 20px" class="center_title">
                    <h2>Запись</h2>
                </div>

                <div class="row">
                    <div class="offset2 span11">
                        <form class="form-horizontal" method="POST" action="main?action=plan.save" name="form" onsubmit="return validate();">

                            <c:url var="planDelURL" value="main">
                                <c:param name="action" value="plan.delete"/>
                                <c:param name="id" value="${plan.id}"/>
                            </c:url>

                            <fieldset>

                                <input type="hidden" name="id" value="${plan.id}">

                                <div class="control-group">
                                    <label class="control-label" for="position">Позиция</label>
                                    <div class="controls">
                                        <input class="span4" type="text" id="position" name="position" 
                                               value="${plan.position}" required>
                                        <span class="help-inline"></span>
                                    </div>
                                </div>

                                <input type="hidden" id="productId" name="productId" value="${plan.getProduct().id}" required>

                                <div class="control-group">
                                    <label class="control-label" for="product">Продукция</label>
                                    <div class="controls controls-row">
                                        <input class="span6" type="text" id="product" name="product" value="${plan.getProduct().name}" required>
                                        <div class="span1 copy_image hint" data-title="Скопировать значение в название плана">
                                            <img src="img/Copy.png" alt="Копировать"/>
                                        </div>  
                                    </div> 
                                </div>

                                <div class="control-group">
                                    <label class="control-label" for="amount">Количество</label>
                                    <div class="controls controls-row">
                                        <input class="span2" type="text" id="amount" name="amount"
                                               value="${plan.amount}" required>
                                        <select class="span2" id="unitId" name="unitId" required>
                                            <option value="${plan.getUnit().id}"><c:out value="${plan.getUnit().symbol}"/></option>
                                            <c:forEach var="unit" items="${unitList}">
                                                <c:if test="${unit.id!=plan.getUnit().id}">
                                                    <option value="${unit.id}"><c:out value="${unit.symbol}"/></option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                        <span class="help-inline"></span>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label" for="name">Название</label>
                                    <div class="controls">
                                        <input class="span4" type="text" id="name" name="name" 
                                               value="${plan.name}" required>
                                        <span class="help-inline"></span>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label" for="price">Стоимость</label>
                                    <div class="controls">
                                        <input class="span4" type="text" id="price" name="price" 
                                               value="${plan.price}" required>
                                        <span class="help-inline"></span>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label" for="begin">Период</label>
                                    <div class="controls controls-row">

                                        <jsp:useBean id="date" class="java.util.Date"/>
                                        <fmt:formatDate pattern="dd.MM.yyyy" value="${plan.begin}" var="beginDate"/>
                                        <fmt:formatDate pattern="dd.MM.yyyy" value="${plan.end}" var="endDate"/>

                                        <input class="span2 tcal" type="text" id="begin" name="begin" 
                                               value="${beginDate}">
                                        <input class="span2 tcal" type="text" id="end" name="end" 
                                               value="${endDate}">
                                        <span class="help-inline"></span>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label" for="comment">Комментарий</label>
                                    <div class="controls">
                                        <input class="span6" type="text" id="comment" name="comment" 
                                               value="${plan.comment}">
                                        <span class="help-inline"></span>
                                    </div>
                                </div>
                            </fieldset>

                            <div class="center_title">
                                <input class="btn btn-primary offset1" type="submit" name="saveButton" value="Сохранить">
                                <a href="main?action=plan.list" class="btn" name="cancelButton">Отмена</a>
                                <c:if test="${plan.id != 0}">
                                    <a href="${planDelURL}" class="btn btn-danger offset2" name="deleteButton">Удалить</a>
                                </c:if>
                            </div>

                        </form>
                    </div>
                </div>     
            </div> 
        </div>                    
    </body>
</html>
