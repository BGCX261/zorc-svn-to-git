<%@tag description="Top of the site" pageEncoding="UTF-8"%>
<%@attribute name="number" required="true" type="java.lang.Integer"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row-fluid navbar navbar-inverse">
    <div class="span10 offset1 navbar-inner">
        <a class="brand" href="#">
            <img src="img/logo.png"> Zorc
        </a>
        <ul class="nav">
            <li class="
                <c:if test="${number==1}">
                    <c:out value="active"/>
                </c:if>"
                ><a href="main?action=plan.list">План</a>
            </li>
            <li class="
                <c:if test="${number==2}">
                    <c:out value="active"/>
                </c:if>"
                ><a href="main?action=production.list">Продукция</a>
            </li>
            <li class="
                <c:if test="${number==3}">
                    <c:out value="active"/>
                </c:if>"
                ><a href="main?action=change.list">Изменения</a>
            </li>
            <li class="
                <c:if test="${number==4}">
                    <c:out value="active"/>
                </c:if>"
                ><a href="main?action=report.prepare">Отчет</a>
            </li>
        </ul>
        <ul class="nav pull-right">
            <li class="
                <c:if test="${number==5}">
                    <c:out value="active"/>
                </c:if>"
                ><a href="main?action=settings.view">Настройки</a>
            </li>
        </ul>
    </div>
</div>