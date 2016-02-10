<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: PaulRyan
  Date: 2/10/2016
  Time: 11:54 PM
  To change this template use File | Settings | File Templates.
--%>
<li>
    <a href="#">${node.name}</a>

    <c:if test="${fn:length(node.childFolders) gt 0}">
        <ul class="nav nav-pills nav-stacked nav-tree">
            <c:forEach var="node" items="${node.childFolders}">
                <c:set var="node" value="${node}" scope="request"/>
                <jsp:include page="get-files.jsp" />
            </c:forEach>
        </ul>
    </c:if>
</li>