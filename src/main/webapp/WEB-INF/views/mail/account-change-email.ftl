<#import "layout/defaultLayout.ftl" as layout>

<@layout.mailLayout>
    ${messageSource.getMessage("account.change_email.email.content", [username, activationUrl], locale)}
</@layout.mailLayout>
