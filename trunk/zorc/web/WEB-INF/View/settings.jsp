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
        <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <title>Настройки</title>
    </head>
    <body>

        <zorc:top number="5"/>

        <div class="row-fluid">

            <div class="well span10 offset1">

                <div style="margin-bottom: 20px" class="center_title">
                    <h2>Настройки</h2>
                </div>

                <div class="row">
                    <div class="offset2 span11">
                        <form class="form-horizontal" method="POST" action="main?action=settings.save">

                            <fieldset>

                                <div id="positionDiv" class="control-group">
                                    <label class="control-label" for="position">Например</label>
                                    <div class="controls">
                                        <input class="span4" type="text" id="position" name="position" 
                                               value="Вот">
                                        <span class="help-inline" ></span>
                                    </div>
                                </div>

                            </fieldset>
                            <div class="center_title">
                                <input class="btn btn-primary offset1" type="submit" name="saveButton" value="Сохранить">
                                <a href="main?action=plan.list" class="btn offset3" name="cancelButton">Отмена</a>
                            </div>

                        </form>
                    </div>
                </div>     
            </div> 
        </div> 
    </body>
</html>
