
<%@ page import="grotrack.Trainer" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'trainer.label', default: 'Trainer')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-trainer" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-trainer" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list trainer">
			
				<g:if test="${trainerInstance?.athletes}">
				<li class="fieldcontain">
					<span id="athletes-label" class="property-label"><g:message code="trainer.athletes.label" default="Athletes" /></span>
					
						<g:each in="${trainerInstance.athletes}" var="a">
						<span class="property-value" aria-labelledby="athletes-label"><g:link controller="athlete" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${trainerInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="trainer.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${trainerInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${trainerInstance?.preferredName}">
				<li class="fieldcontain">
					<span id="preferredName-label" class="property-label"><g:message code="trainer.preferredName.label" default="Preferred Name" /></span>
					
						<span class="property-value" aria-labelledby="preferredName-label"><g:fieldValue bean="${trainerInstance}" field="preferredName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${trainerInstance?.teams}">
				<li class="fieldcontain">
					<span id="teams-label" class="property-label"><g:message code="trainer.teams.label" default="Teams" /></span>
					
						<g:each in="${trainerInstance.teams}" var="t">
						<span class="property-value" aria-labelledby="teams-label"><g:link controller="team" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${trainerInstance?.username}">
				<li class="fieldcontain">
					<span id="username-label" class="property-label"><g:message code="trainer.username.label" default="Username" /></span>
					
						<span class="property-value" aria-labelledby="username-label"><g:fieldValue bean="${trainerInstance}" field="username"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${trainerInstance?.id}" />
					<g:link class="edit" action="edit" id="${trainerInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
