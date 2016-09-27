<#import "layout/defaultLayout.ftl" as layout>

<@layout.mailLayout>
    ${messageSource.getMessage("account.register.email.content", [username, activationUrl], locale)}
</@layout.mailLayout>