<#import "layout/defaultLayout.ftl" as layout>

<@layout.mailLayout>
    ${messageSource.getMessage("account.reset_password.email.content", [username, activationUrl], locale)}
</@layout.mailLayout>
