# SelectGearMotos - Company API

Api do projeto Select Gear Motos para cadastro de Clientes.

## Descrição breve do projeto

Este projeto é uma API para cadastro de clientes. A API foi desenvolvida em Spring Boot 3. Este README fornece instruções para desenvolvedores que desejam rodar o projeto localmente, incluindo como rodar o JAR usando Maven, configurar o ambiente no IntelliJ IDEA, utilizar arquivos `.env` para gerenciar variáveis de ambiente e como rodar o PostgreSQL e o SonarQube usando Docker Compose.
Ainda algumas informações da Arquitetura do projeto.
## Documentação

A documentação do projeto está disponível no [GitHub Wiki](https://github.com/hackthon-fiap-sub/selectgearmotors-client-api/wiki).

<details>

<summary>Por que optamos por um Micro Serviços?</summary>

## Microserviços

> ⚠️"Adotar microserviços cedo demais pode adicionar complexidade desnecessária ao projeto. É preciso primeiro entender bem o domínio do negócio e a arquitetura monolítica antes de fragmentá-lo."  — [Sam Newman](https://samnewman.io/books/building_microservices/)

*Foi optado microserviços porque essa arquitetura permite escalabilidade independente, possibilitando alocar recursos conforme a necessidade de cada serviço, algo não realizavél em um monólito. Além disso, microserviços facilitam o desenvolvimento autônomo, permitindo que equipes pequenas entreguem, testem e implantem suas funcionalidades de forma isolada, acelerando o ciclo de desenvolvimento. O isolamento de falhas garante que problemas em um serviço não derrubem todo o sistema, aumentando a resiliência. Por fim, a flexibilidade tecnológica permite usar a melhor ferramenta para cada caso, proporcionando mais eficiência e inovação no projeto. Como esse projeto tem a tendência de escala, foi optado por microserviços*

Leia mais sobre as motivações da decisão arquitetural no documento: [Decisão de Arquitetura para o MVP](https://github.com/hackthon-fiap-sub/project-docs/wiki/Defesa-da-Decis%C3%A3o-do-uso-Microservi%C3%A7os-na-SelectGearMotors).
E os documentos da Api:
https://github.com/fiapg70/tech-challenge-docs (Olhar)

</details>

<details>

<summary>Por que optamos pelo Modelo Relacional?</summary>

## Modelo Relacional

*Entendemos que o modelo relacional é o que mais se adequa ao nosso problema de negócio, contexto atual e requisitos na API de clientes*

Leia mais sobre as motivações para adoção do modelo relacional em [Decisão de Arquitetura para Banco de Dados](https://github.com/hackthon-fiap-sub/project-docs/wiki/Decis%C3%A3o-de-Banco-de-Dados-SelectGearMotors) na documentação.

</details>

<details>

<summary>Por que optamos por uma SAGA Coreografada?</summary>

## SAGA Coreografada

*Devido a pequena quantidade de membros, optamos pela SAGA coreografada, conforme recomendado por Chris Richardson no livro "[Microservices Patterns](https://www.amazon.com.br/Microservice-Patterns-examples-Chris-Richardson/dp/1617294543)".*

Leia mais sobre as motivações para implementação de uma SAGA coreografada em [Decisão de Arquitetura para SAGA do Projeto](https://github.com/hackthon-fiap-sub/project-docs/wiki/SAGA) na documentação.

</details>

<details>

<summary>Por que optamos por um BFF?</summary>

## Backend for Frontend

*O API Gateway como BFF funciona como um único ponto de entrada para o(s) front-end(s), que não precisam conhecer o endereço de cada um dos serviços no backend. Outra grande vantagem é também a autenticação, realizada pelo próprio API Gateway em conjunto com o IdP Cognito da AWS.*

Leia mais sobre as motivações para implementação do BFF em [Decisão de Arquitetura para o BFF no projeto](https://github.com/hackthon-fiap-sub/project-docs/wiki) na documentação.

</details>

## Arquitetura de Aplicação

Architectural Pattern: [Microservices](https://microservices.io/patterns/microservices.html) + [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)

![Arquitetura de Aplicacao](./docs/arquitetura-de-aplicacao/hackthon-no-saga.png)
[Clique aqui para ver no draw.io](https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=fase5-saga-coreografia.drawio#R%3Cmxfile%3E%3Cdiagram%20name%3D%22Page-1%22%20id%3D%22O3UXYg8NmTAKbsKXbEt2%22%3E7R1bd5s4%2Btf4sT4IcX2M7WS2ZzszmdPZdmdfcoghDlPbcgHnMr9%2BJUAOSJ8BO1xjktYxssAgfffrBM83L78Ezu7xV%2BJ664mquC8TvJioqmlr9JUNvCYDmm4lA6vAd5Mh9Dbw1f%2FHSweVdHTvu16YmxgRso78XX5wSbZbbxnlxpwgIM%2F5aQ9knf%2FWnbPypIGvS2ctj3733egxGbVU8238X56%2FeuTfjAw7%2BWTj8Mnpk4SPjkueM0P4eoLnASFR8m7zMvfWbO34uiTn3Rz59HBjgbeNqpwQ%2FvKfP8zd4udd8Pc378%2BnL89%2FBItP6VWenPU%2BfeBvt3M6cBv4T07kpXcevfLlCH940ZI9lzLBM7KP1v7Wmx%2BWng2uAsf16S3NyZoEdGxLtvTc2WO0WdMjRN8%2BP%2FqR93XnLNk1nynU0LEHso3SzUcqP06%2Fll2VLt6Ovd%2B8rBiYTZ3nUJuuArLfxV%2F5mW4%2F%2BOnd027JTo8C8sPjtzRR8cyYXc1u2Bf567Vwq09eEPkUAq7W%2FopdNSLsS5z0aO09ROyK9P797epLfLTASnrP0Fe4TvjouemDyLvGt4B%2Bq%2FeSGUp38RePbLwoeKVT0k8tO4WoFKVULT1%2BfgNQ1UjHHjPAaVrpoJMixepw7Te4oW9S0DkBjLAERhN9hib6QgIg%2BoxRHhz4wi7pingBsP4b33XZ6SDg0E3eunWurq3nVxcrurS6GFhc3NTaasfXVvlE%2F%2F%2FOqJ9DgYzCOCO0FGmV%2BZqhIHu3D0MnmMzx5Io%2BtsImLF63zoYsZslCOWx2cjadR2eE7BqUUoUD3DoBMZACYUarm6fLm3c6SRUIC%2F29YbdwjNRmSRqdPrdVPd6tPBFMJ%2BeIE9%2B%2BL869t74loR%2F5MV29J1FENqWk8QAGGRApI%2BxOuEse9MF%2FYfcx2xGfXeX6iV4sTC8CUv%2FADe92JIxWgRf%2BXN%2F52zBytgy%2BmgAlDclUACKxRlOAZEiANFvcBsTd08UbPKJqloyn0Oo2hqYmRGPVD8G%2FNFVe21ZJoHV8bY%2FyrxiyIwLyr9sU5y%2BBf5ld8y%2BuhhUxsBwLARlY0ZKvBV4j7cWJzEjkFc%2FevU%2B1g3C6IdsVce8BvkmZ5OJmcb24rmMPrfweGobMOKw2GQeSVbzZ4vfApWs8eAQx1GpcuTn0kNnyKN8NUb4zK0ogzaEpKILgDyGC6AANbJeLgTKINszFNcoht93FtQEGc%2Bu8btgzDm95BcJgASaEVlmMCkhgnDCcJT%2F%2FyuSgCxCekdK18MyFAYjsnGW6uyDVB%2BGud0%2B3pWX03JXHhRsSRI9kRbbO%2BvptVFiYtzlfCJOk4pX%2F24ui11RkchiS5vbl6FqGZB8svYL75QsROcHKi4oeLJnHHqZwawJv7UT%2Bk5e7DWiZ41OvgsB5zUyI5bwwc%2BVbNvC247poA9L17JadOp%2B%2BSe7gbcMPj%2FIOv0YF9VeScHMCf2Zrc6J4jFWh4ATLC%2FaqZerXKuS9eoh%2FuDAdL7g%2Bo%2F%2BUqcK8FXNlamD2J6Y08%2FgDFA%2BrwqgNjsaXEGfaRy5sxmfTz4GLIGFMtYS59J8s%2Ff%2FY33vB1qPLM2WaO1uBHffw7YhbA7XRsABLGEnERgdojWY1RGuwrKRLcAaqjk2rd4BmhqfOzr9bOZH3zNBNhNqFfWVq6Lj2WsP2qaKJE%2BAVGrB9elOsQtO7YBV0AYPX%2F7Lzp4rGj%2F%2BKj3V%2BuHhJr58cvWaPbr3Ap8%2FPtryYjZeyHsOqyHr4RtXHe963b4DSfdEbyelQ6UZqRiNChMT11SNc%2F3CJ5JHSswrEB2ypxRdKHlm6UF2ShNYDS6E1125ubj6epdDZ7db0Xtgt3q2J497dO2tmKgzqYTdYFUAHMAm0qplw%2B0PLVOrFjxIiZVMBLjlOiJShqOnxG5ViB6%2BZg%2FpoFEfkLI26Mj9%2FU77PP0d3f27V1%2F%2Fh%2FerF%2FITNRmjUqYqOZtk58NGtAsVFOttIZ7%2B5PASoqkoAkaLmSamBhCs1TQEBI%2ByoS304XUo3O9alNNkeLcHZqEsdBCy%2BD4ft61iX0rthbr0RwQ1cVQS36mZv79u3CiaMi9pITofK7bFqI3KKrEtZAqrbZ%2BtSSvGFGpYk%2BIKNutRkeLqUADpde6%2BNTqjU%2BVRFr8oeUO0WmvexByBFZJT%2Bhy%2F981MO0ShGt9K%2FDqTLjNL%2FUekf28L2yZFa7Ur%2Fl%2B5JMaoKjbhf5L2CefuiNpLTofKNbMZKWSr9m2pN0r90oaal%2FwpOu1H676f0jwRpwe44MNioIC1cFtlSK5Ito3b%2Bc4TaiLHk5xotNKwWX6hhsmWMClC%2FFKDwaVkHSTNF9wcgQaOpYcpUTWuMqo1hLbDIUE7VWgprEana2cKYSNXaFsaMCnL%2FSNWGR9UMUVADvIJtU7UKft2LompaVVuBUbun8BhVyxOjs4P1RKrWdrCeMYaqfEiqJshqGu5eVjONLojY0cXsOFYOCcmtuibUtCuZj0vmi5kDwvySWDykWuDdnUzfpNvAuFX6Zg4stY3jbbkxVesnGKvaSWBsoQKwrA0IKuS25XiayHUAdqFCPAQaM%2BVBJE%2BDeAnEYKAxUx5E8rSEEerAIDRm6vIdi2cj4GwknB3zv5PM0oKMcG0i3ZwVygi9M0LDRT68BPkTPj%2Bjh1Apz7ybtAZJQAxgUmSvNfdQ1%2B21Jmj%2F79D6g3xzfovwX993%2Fvdg%2B4kzlsEQZMC7BT4YasZMfHIMvlQboAX6KvsWDrn9IplNUWO%2FWV8tIxKcj7GZ3QXpSx1StIg71SK4Gqucww2uXZkBOrUBQIhYyO17Ei9gmgMjeFUlUNxTRepECbSV6grm0LheVSDgod8DBwLuIq0LCMAsvQoVknsNFJXTEWtXTasSYPBueCRGZ8ZzG6Ms50RTBeN2eSe0c7B03o%2BKORrPrKtoHCuZ3ww6o07Q9wBXOZiKVe5ikGoBelA%2F0pAPcrqQ6He03pIwH6eNWxqFHm1ghsnqxL8ZF9zJQCAobwYqAYLi%2Bc0AwdB0g8pAYLbkh0W2zWo0HH74gqabaIuqeMPeBr4%2BGRGPsPrTn5ydL%2B000I9HsnHqV8bcMrI2TnTUICKaJQUD6%2BFSUJXDKtBxQq6FCrPjfNsewOpoN2U5seSQxF1St%2FXCt8aE6u63uzUVApNH50xurD%2FOmYXJas9einNm7WzuXaceRDS1ab7cDYaqidgyIvKx%2BhFRDnD84mxcVnv3ak%2BFjICuciChZu%2Fr7ZpYXGhNXuhW61xbnUh9GYPEQVX8a5LVFNsxRfTfjA%2FrthJyPBWzqbFlZCctI5GdVwGAnlC8LFkO21EdHSNhm1q5fNOfNG03bpl4FzcPEqM1r008s44DcQ2bZwibB5TM0ACWWIfDFUb6%2FlrowfsFkj%2BKQLInpLUHWa5HBNnBZ7kmEuvdw367TG6zDizVhEJqAJY2JbjCWNqJg%2Bd8LAVKSBfhRceGVKypud1GJYbUkvnvNqQWLemYKF8%2FCWk4UV4V0%2FqAPncawPK1poiJHCu8Yx3il6%2BgpbD36q%2BGdUH%2FBSRiC1jhOtRfeIW1YZFrBNQhKyJCXYfCCKlpSDEL6XXJ%2FGboNapQIqA%2FSlHXtauEZC7u5MqSSASQSP04ZL0PgcdW7x%2BjFShW2mv1DkMS1Os9CR0fHqeVVhfoBdqUnRnW8vuVcFnOaKuaL1DtUWrvg2LZgLGMYfjCfcsqUEqhMd8yvDNjdnu%2FstubacSAkSwRaYCNX8NTtSFIwwOzQfFWxuWm4p654SqURhkVh7eWF3l%2FCkaySNJU0dtCaBoNdfWrDo1XtBTCKFQZlpqqaAnDkuySH5n7ByhdY%2BS9SyBzb650TWFaTM4oTNz98tJFfA11LeJjGelbLo2m5MOpGBY2FVFVyFPLRbnaE3bft3Od%2BAGyvR31Sa63I56c2toxAwaWijoGAzRQMOi8D4Jp5HdOM9Webh3qrBEamOXTj04Ilda9KE%2B5NBTV6qwDBZxcVUGJGte9%2FnWHElWMNXNKLInr0fcr9v53lvA1Dzyq9rv8c%2Fp12SnSbo35Lbmx3uS3HDEVfNT8lo2zdVaee0ef1nM2%2FnZ190CCux%2FOw4%2Bakl4wztsQDL2aPaoOxQ4mpQOqyQE%2FQFVSqtZNSs8KTtGEuitGWWGH4vkNZWVXovO3Sf7o1%2F1y6YXhw349EvuR2I%2FEPoO7QilCgxcF6IzY96MF0vnEHggc7zOxV4XIwjJiXzK%2FGWIPRQgcJfY3jr8epfqR0I%2BEviiEv3NCD4UV7sMly2R3HZeE7C%2BhL7fMhxORUMLc3scaHmILeYUVwA%2FDE29q98bCay5LzeCap4WBh7jmtphJgYHMt3ZXvcchnjCUACIM%2FGC1hx2dJcJYQmk4pFgtFAoFwhb9LYv%2FIAGchfRxHc66Lm4AUEsK2QDO1eFxLgTNIjUiJz%2BAskoRnVsLgoZEAE%2BURETh4dm7Z8Eb4XRDtisS5%2FqLoVMsPu1mcb24rmEPbaEHgQX0wrWAHawj7QDeQTlyaLb4zDFseJxJzDywAbNqU5kH8AKr5SgyhmYNLu7aMERiDNWsgiT9GrqJgICGKwBarwUiHllWLhDVbtN5H4ZXcJyMGD644EvdEqulURxvtXUgDG2yQPzVi5915TC7HInhnnLlfxymZNIvUe4d%2F8UJ2Bym5N%2BzGVv2kgjSTuCT4fF5HdnC7thA%2BU77UFmwFV6PZWFqHvjx0nthRH7uk40KnHgHngjfkmiAG2Bgs3T5WxW0cCd9RbIxlOokG0Opm9bk1CDK85knBiozgA4R1A9rgi3krZmGmoWA0vlGG11KuC7Rh7YGh4P39DR4H%2Bj0xJemCzq0me9aVDrfwC340jBQZTtJk%2FDJ9sIMV4Yp8moTstG3arnCFRzgo9A%2BOLXctNSpUHTN1OQ2n%2B0q5kOPDcdVFXOOVD1RzPGomPcLx%2BtRzE2kTi010%2BlEkAaAQrkt6%2Bi4B2Vcx6pQNXgPdN5avoWqUDAkyTUeZovbgyApQVXvzQbiCpuqXWmFGzMb6P1tR1rYU6yUGeu15zaepa2ZYuhrSc1caX4bkY%2BabFR9Q7I4i4kHOrr%2B01uQYzJ0H4gj9B5y85om%2FcdFDFEo4ReqRP8PlEEkGQfGUMQCyro38OfkmBZT%2FKRZAj3Cx%2Bh%2FqkWncX47sKNDuA1r0lyl3riykw8K5zObolZGJ6rDuf1SzqdynHiVqxz9yovk9w0QEopTtyQhJSM9aJke%2FKyJHpi62KrKQLL8ApaRst8PcaBRVpUNjWM3nkF049EP%2FuWs7MsTvXMNeQw%2Bs3aCZVVQVXstDxtVjVO8p1vXmUCCNwIp6Y0dTQUqOaEZidiQbWeut6arcnFht4fibpmwWwBx23VfGBVsyqNpc3DuCw1LEUcQk2jVfWFUKDPabw4BFAYoRKqe6BJGBdY84vjg3BdYt%2BS4QhPA8ra9FmavbAtl8S7n1x0rrK9USiT60YlMLAZiGqcVDxHmN1Q8BI8AVUEv6QVAiQUHygCqZH5DAGX3CaCas36aVX08XEvricRintp7cKwEMVaC%2BKCVIBDvQ8bj47kHu6SBMx%2Br39In5%2Br%2ByaIFRATtfQiB6CG2gC6OrYYQWLLT53r7lOR93PL8DsVhL0kzJG94i65q%2BhSZyttPnv3bQAcExFNv29mECm0UZd4jRxy55HkCtEnJtjeBAqoEaqor7HdSf%2F46SOIlWBEJI3l48JfeNPSCJ%2Fo3nO724ePdlkT%2BA%2B%2BSkX5UE%2B2TSkXo1WJw9aaMWNbAony4s6Jce6jd%2F32W%2BoBsyUlRnJRRdkJDFc5ks3lKoBd%2BuCNb%2F4n8lsGKMehnSE7%2BuoJ%2BVIG3IQUIKTYB6nWod1Q%2F%2BepG8T3kNpq59hCTwrTGTB8IU8vqy5%2BUqYIO%2FQVqt8IUZkuX1xToVzsBWxYpv0ZOtI9rh7H04RgjJnM8uTLjVzzEFGIph9sExMhWRXm7axu4hrCIM4rdsuVSrYgzrbXgoIcBIVFWFmB0%2F1fiemzG%2FwE%3D%3C%2Fdiagram%3E%3C%2Fmxfile%3E)↗️

<!-- https://www.milanjovanovic.tech/blog/what-is-a-modular-monolith -->

## DER da Aplicação:

![DER da Aplicação](./docs/arquitetura-de-aplicacao/der-client.png)
<br />
[Clique aqui para ver no draw.io](https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=cliente-api.drawio#R%3Cmxfile%3E%3Cdiagram%20name%3D%22Page-1%22%20id%3D%22MLu7NkJxeQt5YtbHqxJA%22%3E7Z1hk6I4Gsc%2FTVftvegtAUF9Od0zc7d1PVd7M7u1d6%2B6IkRlFwgLsbudT78JJAomKCii23lmp2Y1xoB5%2FskveZ4k3DmP8ds%2FM5SuvpAAR3f2KHi7cz7e2fZkNmb%2F8oRNmTB2p2XCMguDMsnaJXwLv2OROBKp6zDAeS0jJSSiYVpP9EmSYJ%2FW0lCWkdd6tgWJ6ldN0RIrCd98FKmpv4UBXZWpU3e0S%2F8XDpcreWVrJD6JkcwsEvIVCshrJcn5dOc8ZoTQ8lX89ogjXneyXsrvfW74dHtjGU5omy%2FET3G%2Btr674%2FuX9M%2Bn1Zfxy%2Bj3e8sR9nlB0Vr8ZHG7dCPrICPrJMC8mNGd8%2FC6Cin%2BliKff%2FrKjM7SVjSO2DuLvVyEUfRIIpIV33UWLv%2BPpec0I3%2Fgyide8Yd%2FgyS0kl7%2BYenixnBG8Vvjb7a2NckUiEmMabZhWcQXPFH3QnzSFK87S3ozkbaqWHEizYuEepbbkncVzF6IOu5S35ra9iJ22Yc5e7Gkxc8uE3i11Ozg%2Fbkm8oP7vGgpH1gGVh9v5dfE57KgHEesRSwxymJCSZbf%2B1HI6u0epaG8BvsN5WXql2bJyu0E4ct%2BUp6i5Kw7VH77x09fD90DSyuvqSTX7m5Pvkw%2BtK5RFIXLhL32WXVgJrsHLrKQNfoP4oM4DAL%2Bda3Y682hB5Xak7pMx6pM7alGps6lVKr2CTEOQqTULOvSUv6SonlRWzlFGRV9uMMrh%2FXKFIUJr%2BOi4n0SRSjNwyJ7mbIKo%2BAJbciayoLkO9aVvOHga9mFW0XFvz6xwnJR81xl38TNaK2aYa7BJ5RTkeNAN7VY2L6v66YCb%2B65Xj%2BGntbtbGn6o20fVTW0dbH%2ByD3e%2B9eM%2FJUT7GFFsvA7N20kTFE1fPH%2BNYwjlDA0omAv6YEUQ4HRng0SkmBFIDxTkJH0F5QtMRUJKQkTWtSD%2B8D%2Bspp5HP3o3rnsXh%2FZe2v3nv3l2TNGl4TZlQmRl4GZHl4x18QDJakoNMILWX4m6p2%2FnhNKSSwk00oAze1JVcWm3oyPqeBizd1TRPDzvxtlwH4sDVH0laEFJcuoNFox9kI7o2ksq63rbf3uV%2Fx%2B2yas2hdRMXxasb4ZJ8cHIqeby9Wbq2Ifp6N5RGG7SutcGopYn5YgytoPo0%2Bu2Hx7n6fLYKLIgGW3R%2FNwmeOMmZy9TggfkiRrXthV9SH7%2BjIvH4n4YbJ8Kr%2Fp7QnIvREBvTW2d0s3Cj1dUK2KG0BRU6BLK7q0HkeeQBfv2nSZtRfBMH0HG8rK8gZHy9RMtEhvTkUECYpxkZL5K5T9YLvuP%2F5WgLkF2RgHFEvnPwGiDEuU2bWJYtmAlP02YSBTHEUFKfe575hijdgfoEpn5ZiHlRZBEMDKhbHS2ht6Oa508IYawJUGc717rqju0CL68Uw3aXXG4gFauovHPLSoXtUyDIuFniCk1mdIrfXk5HIhNQu8nkPE1MqGdbtBNUv1e5odVbMM9X1K1UFc7WISMm5UYYMfdIgJ6xHGXD20Jq0OE9ZdozAQMKojFIJrPejGPKqAG%2FQGqHL18JoNblClURhIFdUNCuG1XpRjHldUHyhwZXCuXD%2B%2BZnfwjBoAlgZzvXuwqI5RiK%2F1JR7j2CIvqcTXDhPmDiJrxyJrzt7mWS0%2FtCqwLwUQB9yeQ4TWyjbVKbTWtXM5Twaq29Ps0JpjqOfTUT2fEFrrV0LNA4pt%2BKMfSbUqbghNgRN0iMnqEcboQmvDMgacoEqjMBAwqhMUQms96MY8qoAL9AaoogutDUsV8IAqjcJAqqgeUJ8EQJVzdWMcVaZwfNMNUKW9Y%2FRSWJmqw1SDsTI19PymqTrGxDEKI%2BDKmcIxjivyAErgylW54l6bK%2BMO8bb3z5Vxg7neO1fGargtJvMw2puwHD0VeI78P5bF4bn3fqkBfjhwmIRcN3e684H3WdVwCjAg7KhGjUPYBBB2CwibXhthE0CY0irMQ9hERViAcz8LUxqSpMIxF5a0n6If8%2FCirjwBvAyOl%2B1TYo7h5WIrEicdVosYgBfnJLwcsk53vOyXNkRvoPrhURBkOM%2FB93amdDqS5XQptSpuCC118OUDWS5Glqsvcp50WDFiAFk8Qycu6oKRAFH0nGbEZ3hhHfYzs3Re7HcZzQmJcOF8A8h0UpFx05epOh8GyAwPmauvcp6q09jP195Jc9WlA7aZmJk2PPHvWW6nCQvAAFY6qcY4rEw6POsHsHIxrFx9mbOM%2BgBWqu3CQKyowbdyc35x1gPA5RztGAcXV3WMCTGlq03On958mDV7XIEjH1QxbVcyywdR68L3uidRb59g3f8DimHjVLshxZlPLG3wjBw48mHQJ5K7qh%2FM7CMfXEP3TrnqFAOOfOhXQgdGFm6%2FI4s2xQ2gKU%2BdrgBj%2Bp%2B2HmGMzhk6KGM8WCqoNArzAOOpoZGc%2BMzW5XR1e%2FjtmC%2Fm%2BPU%2FP%2F33109%2FK%2BTchIjMQwysF7wBxOgco8MiBpYLKo3CQMSoywW3iHkOQtZnU3%2F1HLB7YJnE%2FwAwnSRkHGBmcDTqDQBGexTEoISZqcNXgwkzM%2FRg1Jk63AyIv455tKQ2jbFHMI05XUbGUcaDxem3QBndwRDDzmNgcbrSKsyjjKdZnC4pw6YxNAt9WmENPNDjBA0Zh5gZnL99C4hpu%2FLjchMZOIBbaRXmIWamDjiriAFP2dkKMg8wsKLsBgCjPbphWMDAWdxKqzAQMOp6snmYUYDKWaoxDirWCNaQ3QJVxtemijVSA3FG730SLcM8slgjNRQnNqzAxqdzhGMcXKZTRRg4WGK5cJx1yyuyJAmKPu1SH4qDdrFkxi7PEyk0wW3zO6Z0I1iD1pTULcesk23%2BJ3sQ%2Fub%2F%2FM2Prnz78a364UfJovJe%2BQ022lsk5WSd%2BfjQDy%2FzUQmtxny2XikZjhANX%2Bo30v9u15mh5rEnLe3TtIdxGPtYI9dQA8n9%2BEcN1NjXtrZQ8dUPWYY2lQxigLkr%2BWdSIk904WM5XJP7DcUegM8N%2BWdnZZ%2BIZzo2Zbesg%2FnZi%2FL39TxKUN0QYpQQ4SXsaW0xeji8OGM8qVvVHWnGDRMN6Lceqgt0SHBORrvJ4rkjyKleGgd2teqUcLnJoqV6Dcze1iqahoGzRUv1G8DG1p5FdGDmOO535timuEFUpfoggDT9uyWPkUa3t3Vg0nTYgGaAU9Jq8Bi8f8yoy6t8Eqco2cD%2B1h51ZCBp4Fm0t0Aa3RbXgUkDD6NV24WJpFHXWS341RB%2F5BIsrzhLPObhxVanx4CX4fGi3eA6LF%2Bk4YEvlYZhIF9sdUYrTlFIUFx9Nq1lw8ajEwRkIGNg69FNMEa3vXVgxsDmI7VhmMgYdS67QKyl5xs9ZDwU81pP5nla1DhAp6OizIOOvCZA57rQ0W14HRY6Dqwc1zUNA7HjqFNcWDneg3AMpMtEUQYsfW0nlgsvfZWTHLlCYHbWYlP2NiOsN6hkZw1q9YUEmOf4Cw%3D%3D%3C%2Fdiagram%3E%3C%2Fmxfile%3E)↗️

## Arquitetura Cloud

Cloud provider: AWS

[Clique aqui para ver no draw.io](https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=fase5-saga-coreografia.drawio#R%3Cmxfile%3E%3Cdiagram%20name%3D%22Page-1%22%20id%3D%22O3UXYg8NmTAKbsKXbEt2%22%3E7R1bd5s4%2Btf4sT4IcX2M7WS2ZzszmdPZdmdfcoghDlPbcgHnMr9%2BJUAOSJ8BO1xjktYxssAgfffrBM83L78Ezu7xV%2BJ664mquC8TvJioqmlr9JUNvCYDmm4lA6vAd5Mh9Dbw1f%2FHSweVdHTvu16YmxgRso78XX5wSbZbbxnlxpwgIM%2F5aQ9knf%2FWnbPypIGvS2ctj3733egxGbVU8238X56%2FeuTfjAw7%2BWTj8Mnpk4SPjkueM0P4eoLnASFR8m7zMvfWbO34uiTn3Rz59HBjgbeNqpwQ%2FvKfP8zd4udd8Pc378%2BnL89%2FBItP6VWenPU%2BfeBvt3M6cBv4T07kpXcevfLlCH940ZI9lzLBM7KP1v7Wmx%2BWng2uAsf16S3NyZoEdGxLtvTc2WO0WdMjRN8%2BP%2FqR93XnLNk1nynU0LEHso3SzUcqP06%2Fll2VLt6Ovd%2B8rBiYTZ3nUJuuArLfxV%2F5mW4%2F%2BOnd027JTo8C8sPjtzRR8cyYXc1u2Bf567Vwq09eEPkUAq7W%2FopdNSLsS5z0aO09ROyK9P797epLfLTASnrP0Fe4TvjouemDyLvGt4B%2Bq%2FeSGUp38RePbLwoeKVT0k8tO4WoFKVULT1%2BfgNQ1UjHHjPAaVrpoJMixepw7Te4oW9S0DkBjLAERhN9hib6QgIg%2BoxRHhz4wi7pingBsP4b33XZ6SDg0E3eunWurq3nVxcrurS6GFhc3NTaasfXVvlE%2F%2F%2FOqJ9DgYzCOCO0FGmV%2BZqhIHu3D0MnmMzx5Io%2BtsImLF63zoYsZslCOWx2cjadR2eE7BqUUoUD3DoBMZACYUarm6fLm3c6SRUIC%2F29YbdwjNRmSRqdPrdVPd6tPBFMJ%2BeIE9%2B%2BL869t74loR%2F5MV29J1FENqWk8QAGGRApI%2BxOuEse9MF%2FYfcx2xGfXeX6iV4sTC8CUv%2FADe92JIxWgRf%2BXN%2F52zBytgy%2BmgAlDclUACKxRlOAZEiANFvcBsTd08UbPKJqloyn0Oo2hqYmRGPVD8G%2FNFVe21ZJoHV8bY%2FyrxiyIwLyr9sU5y%2BBf5ld8y%2BuhhUxsBwLARlY0ZKvBV4j7cWJzEjkFc%2FevU%2B1g3C6IdsVce8BvkmZ5OJmcb24rmMPrfweGobMOKw2GQeSVbzZ4vfApWs8eAQx1GpcuTn0kNnyKN8NUb4zK0ogzaEpKILgDyGC6AANbJeLgTKINszFNcoht93FtQEGc%2Bu8btgzDm95BcJgASaEVlmMCkhgnDCcJT%2F%2FyuSgCxCekdK18MyFAYjsnGW6uyDVB%2BGud0%2B3pWX03JXHhRsSRI9kRbbO%2BvptVFiYtzlfCJOk4pX%2F24ui11RkchiS5vbl6FqGZB8svYL75QsROcHKi4oeLJnHHqZwawJv7UT%2Bk5e7DWiZ41OvgsB5zUyI5bwwc%2BVbNvC247poA9L17JadOp%2B%2BSe7gbcMPj%2FIOv0YF9VeScHMCf2Zrc6J4jFWh4ATLC%2FaqZerXKuS9eoh%2FuDAdL7g%2Bo%2F%2BUqcK8FXNlamD2J6Y08%2FgDFA%2BrwqgNjsaXEGfaRy5sxmfTz4GLIGFMtYS59J8s%2Ff%2FY33vB1qPLM2WaO1uBHffw7YhbA7XRsABLGEnERgdojWY1RGuwrKRLcAaqjk2rd4BmhqfOzr9bOZH3zNBNhNqFfWVq6Lj2WsP2qaKJE%2BAVGrB9elOsQtO7YBV0AYPX%2F7Lzp4rGj%2F%2BKj3V%2BuHhJr58cvWaPbr3Ap8%2FPtryYjZeyHsOqyHr4RtXHe963b4DSfdEbyelQ6UZqRiNChMT11SNc%2F3CJ5JHSswrEB2ypxRdKHlm6UF2ShNYDS6E1125ubj6epdDZ7db0Xtgt3q2J497dO2tmKgzqYTdYFUAHMAm0qplw%2B0PLVOrFjxIiZVMBLjlOiJShqOnxG5ViB6%2BZg%2FpoFEfkLI26Mj9%2FU77PP0d3f27V1%2F%2Fh%2FerF%2FITNRmjUqYqOZtk58NGtAsVFOttIZ7%2B5PASoqkoAkaLmSamBhCs1TQEBI%2ByoS304XUo3O9alNNkeLcHZqEsdBCy%2BD4ft61iX0rthbr0RwQ1cVQS36mZv79u3CiaMi9pITofK7bFqI3KKrEtZAqrbZ%2BtSSvGFGpYk%2BIKNutRkeLqUADpde6%2BNTqjU%2BVRFr8oeUO0WmvexByBFZJT%2Bhy%2F981MO0ShGt9K%2FDqTLjNL%2FUekf28L2yZFa7Ur%2Fl%2B5JMaoKjbhf5L2CefuiNpLTofKNbMZKWSr9m2pN0r90oaal%2FwpOu1H676f0jwRpwe44MNioIC1cFtlSK5Ito3b%2Bc4TaiLHk5xotNKwWX6hhsmWMClC%2FFKDwaVkHSTNF9wcgQaOpYcpUTWuMqo1hLbDIUE7VWgprEana2cKYSNXaFsaMCnL%2FSNWGR9UMUVADvIJtU7UKft2LompaVVuBUbun8BhVyxOjs4P1RKrWdrCeMYaqfEiqJshqGu5eVjONLojY0cXsOFYOCcmtuibUtCuZj0vmi5kDwvySWDykWuDdnUzfpNvAuFX6Zg4stY3jbbkxVesnGKvaSWBsoQKwrA0IKuS25XiayHUAdqFCPAQaM%2BVBJE%2BDeAnEYKAxUx5E8rSEEerAIDRm6vIdi2cj4GwknB3zv5PM0oKMcG0i3ZwVygi9M0LDRT68BPkTPj%2Bjh1Apz7ybtAZJQAxgUmSvNfdQ1%2B21Jmj%2F79D6g3xzfovwX993%2Fvdg%2B4kzlsEQZMC7BT4YasZMfHIMvlQboAX6KvsWDrn9IplNUWO%2FWV8tIxKcj7GZ3QXpSx1StIg71SK4Gqucww2uXZkBOrUBQIhYyO17Ei9gmgMjeFUlUNxTRepECbSV6grm0LheVSDgod8DBwLuIq0LCMAsvQoVknsNFJXTEWtXTasSYPBueCRGZ8ZzG6Ms50RTBeN2eSe0c7B03o%2BKORrPrKtoHCuZ3ww6o07Q9wBXOZiKVe5ikGoBelA%2F0pAPcrqQ6He03pIwH6eNWxqFHm1ghsnqxL8ZF9zJQCAobwYqAYLi%2Bc0AwdB0g8pAYLbkh0W2zWo0HH74gqabaIuqeMPeBr4%2BGRGPsPrTn5ydL%2B000I9HsnHqV8bcMrI2TnTUICKaJQUD6%2BFSUJXDKtBxQq6FCrPjfNsewOpoN2U5seSQxF1St%2FXCt8aE6u63uzUVApNH50xurD%2FOmYXJas9einNm7WzuXaceRDS1ab7cDYaqidgyIvKx%2BhFRDnD84mxcVnv3ak%2BFjICuciChZu%2Fr7ZpYXGhNXuhW61xbnUh9GYPEQVX8a5LVFNsxRfTfjA%2FrthJyPBWzqbFlZCctI5GdVwGAnlC8LFkO21EdHSNhm1q5fNOfNG03bpl4FzcPEqM1r008s44DcQ2bZwibB5TM0ACWWIfDFUb6%2FlrowfsFkj%2BKQLInpLUHWa5HBNnBZ7kmEuvdw367TG6zDizVhEJqAJY2JbjCWNqJg%2Bd8LAVKSBfhRceGVKypud1GJYbUkvnvNqQWLemYKF8%2FCWk4UV4V0%2FqAPncawPK1poiJHCu8Yx3il6%2BgpbD36q%2BGdUH%2FBSRiC1jhOtRfeIW1YZFrBNQhKyJCXYfCCKlpSDEL6XXJ%2FGboNapQIqA%2FSlHXtauEZC7u5MqSSASQSP04ZL0PgcdW7x%2BjFShW2mv1DkMS1Os9CR0fHqeVVhfoBdqUnRnW8vuVcFnOaKuaL1DtUWrvg2LZgLGMYfjCfcsqUEqhMd8yvDNjdnu%2FstubacSAkSwRaYCNX8NTtSFIwwOzQfFWxuWm4p654SqURhkVh7eWF3l%2FCkaySNJU0dtCaBoNdfWrDo1XtBTCKFQZlpqqaAnDkuySH5n7ByhdY%2BS9SyBzb650TWFaTM4oTNz98tJFfA11LeJjGelbLo2m5MOpGBY2FVFVyFPLRbnaE3bft3Od%2BAGyvR31Sa63I56c2toxAwaWijoGAzRQMOi8D4Jp5HdOM9Webh3qrBEamOXTj04Ilda9KE%2B5NBTV6qwDBZxcVUGJGte9%2FnWHElWMNXNKLInr0fcr9v53lvA1Dzyq9rv8c%2Fp12SnSbo35Lbmx3uS3HDEVfNT8lo2zdVaee0ef1nM2%2FnZ190CCux%2FOw4%2Bakl4wztsQDL2aPaoOxQ4mpQOqyQE%2FQFVSqtZNSs8KTtGEuitGWWGH4vkNZWVXovO3Sf7o1%2F1y6YXhw349EvuR2I%2FEPoO7QilCgxcF6IzY96MF0vnEHggc7zOxV4XIwjJiXzK%2FGWIPRQgcJfY3jr8epfqR0I%2BEviiEv3NCD4UV7sMly2R3HZeE7C%2BhL7fMhxORUMLc3scaHmILeYUVwA%2FDE29q98bCay5LzeCap4WBh7jmtphJgYHMt3ZXvcchnjCUACIM%2FGC1hx2dJcJYQmk4pFgtFAoFwhb9LYv%2FIAGchfRxHc66Lm4AUEsK2QDO1eFxLgTNIjUiJz%2BAskoRnVsLgoZEAE%2BURETh4dm7Z8Eb4XRDtisS5%2FqLoVMsPu1mcb24rmEPbaEHgQX0wrWAHawj7QDeQTlyaLb4zDFseJxJzDywAbNqU5kH8AKr5SgyhmYNLu7aMERiDNWsgiT9GrqJgICGKwBarwUiHllWLhDVbtN5H4ZXcJyMGD644EvdEqulURxvtXUgDG2yQPzVi5915TC7HInhnnLlfxymZNIvUe4d%2F8UJ2Bym5N%2BzGVv2kgjSTuCT4fF5HdnC7thA%2BU77UFmwFV6PZWFqHvjx0nthRH7uk40KnHgHngjfkmiAG2Bgs3T5WxW0cCd9RbIxlOokG0Opm9bk1CDK85knBiozgA4R1A9rgi3krZmGmoWA0vlGG11KuC7Rh7YGh4P39DR4H%2Bj0xJemCzq0me9aVDrfwC340jBQZTtJk%2FDJ9sIMV4Yp8moTstG3arnCFRzgo9A%2BOLXctNSpUHTN1OQ2n%2B0q5kOPDcdVFXOOVD1RzPGomPcLx%2BtRzE2kTi010%2BlEkAaAQrkt6%2Bi4B2Vcx6pQNXgPdN5avoWqUDAkyTUeZovbgyApQVXvzQbiCpuqXWmFGzMb6P1tR1rYU6yUGeu15zaepa2ZYuhrSc1caX4bkY%2BabFR9Q7I4i4kHOrr%2B01uQYzJ0H4gj9B5y85om%2FcdFDFEo4ReqRP8PlEEkGQfGUMQCyro38OfkmBZT%2FKRZAj3Cx%2Bh%2FqkWncX47sKNDuA1r0lyl3riykw8K5zObolZGJ6rDuf1SzqdynHiVqxz9yovk9w0QEopTtyQhJSM9aJke%2FKyJHpi62KrKQLL8ApaRst8PcaBRVpUNjWM3nkF049EP%2FuWs7MsTvXMNeQw%2Bs3aCZVVQVXstDxtVjVO8p1vXmUCCNwIp6Y0dTQUqOaEZidiQbWeut6arcnFht4fibpmwWwBx23VfGBVsyqNpc3DuCw1LEUcQk2jVfWFUKDPabw4BFAYoRKqe6BJGBdY84vjg3BdYt%2BS4QhPA8ra9FmavbAtl8S7n1x0rrK9USiT60YlMLAZiGqcVDxHmN1Q8BI8AVUEv6QVAiQUHygCqZH5DAGX3CaCas36aVX08XEvricRintp7cKwEMVaC%2BKCVIBDvQ8bj47kHu6SBMx%2Br39In5%2Br%2ByaIFRATtfQiB6CG2gC6OrYYQWLLT53r7lOR93PL8DsVhL0kzJG94i65q%2BhSZyttPnv3bQAcExFNv29mECm0UZd4jRxy55HkCtEnJtjeBAqoEaqor7HdSf%2F46SOIlWBEJI3l48JfeNPSCJ%2Fo3nO724ePdlkT%2BA%2B%2BSkX5UE%2B2TSkXo1WJw9aaMWNbAony4s6Jce6jd%2F32W%2BoBsyUlRnJRRdkJDFc5ks3lKoBd%2BuCNb%2F4n8lsGKMehnSE7%2BuoJ%2BVIG3IQUIKTYB6nWod1Q%2F%2BepG8T3kNpq59hCTwrTGTB8IU8vqy5%2BUqYIO%2FQVqt8IUZkuX1xToVzsBWxYpv0ZOtI9rh7H04RgjJnM8uTLjVzzEFGIph9sExMhWRXm7axu4hrCIM4rdsuVSrYgzrbXgoIcBIVFWFmB0%2F1fiemzG%2FwE%3D%3C%2Fdiagram%3E%3C%2Fmxfile%3E)↗️

*Mais detalhes sobre a motivação para adoção de cada serviço de nuvem estão disponíveis no diagrama.*

>[!TIP]
> Uma versão em alta definição do diagrama está disponível em formato SVG na pasta `\docs\arquitetura-cloud`

## API do Backend - status
[![Deploy to Amazon EKS](https://github.com/hackthon-fiap-sub/selectgearmotors-client-api/actions/workflows/build-eks.yml/badge.svg)](https://github.com/hackthon-fiap-sub/selectgearmotors-client-api/actions/workflows/deploy.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=hackthon-fiap-sub_selectgearmotors-client-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=hackthon-fiap-sub_selectgearmotors-client-api)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=hackthon-fiap-sub_selectgearmotors-client-api&metric=coverage)](https://sonarcloud.io/summary/new_code?id=hackthon-fiap-sub_selectgearmotors-client-api)

### Licença da API
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

### 📋 Pré-requisitos

Ferramantas que precisam estar instaladas para rodar o projeto

```
- Java 17+
- Maven 3.6+
- Docker e Docker Compose
- IntelliJ IDEA
- AWS EKS
- AWS Lambda
- AWS RDS
- AWS SNS
- AWS SQS
- Zipkin
- OWASP ZAP
- Sonar
- Spring boot
- Postgres
- Terraform
```
## 🛠️ Construído com

Ferramentas e links para os projetos originais

* [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - A versão mínima do JDK utilizada.
* [Maven 3.6+](https://maven.apache.org/) - Gerenciador de dependências e build.
* [Docker](https://www.docker.com/) - Ferramenta para criação e gerenciamento de containers.
* [Docker Compose](https://docs.docker.com/compose/) - Orquestração de múltiplos containers Docker.
* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - IDE para desenvolvimento Java.
* [AWS EKS](https://aws.amazon.com/eks/) - Serviço gerenciado de Kubernetes na AWS.
* [AWS Lambda](https://aws.amazon.com/lambda/) - Serviço de computação serverless da AWS.
* [AWS RDS](https://aws.amazon.com/rds/) - Banco de dados relacional gerenciado na AWS.
* [AWS SNS](https://aws.amazon.com/sns/) - Serviço de mensagens de publicação/assinatura.
* [AWS SQS](https://aws.amazon.com/sqs/) - Serviço de fila gerenciada da AWS.
* [Zipkin](https://zipkin.io/) - Ferramenta para rastreamento distribuído.
* [OWASP ZAP](https://www.zaproxy.org/) - Scanner de segurança para aplicações web.
* [SonarQube](https://www.sonarqube.org/) - Plataforma de análise de qualidade de código.
* [Spring Boot](https://spring.io/projects/spring-boot) - Framework para criação rápida de APIs Java.
* [PostgreSQL](https://www.postgresql.org/) - Banco de dados relacional utilizado.
* [Terraform](https://www.terraform.io/) - Ferramenta de IaC (Infraestrutura como Código).
* [Maven](https://maven.apache.org/) - Gerente de Dependência
* [ROME](https://rometools.github.io/rome/) - Usada para gerar RSS

## Stack

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=Kubernetes&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/kubernetes-%23326ce5.svg?style=for-the-badge&logo=kubernetes&logoColor=white)
![Terraform](https://img.shields.io/badge/terraform-%235835CC.svg?style=for-the-badge&logo=terraform&logoColor=white)

## 🚀 Começando

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento e teste.

Consulte **[Implantação](#-implanta%C3%A7%C3%A3o)** para saber como implantar o projeto.

## 📦 Implantação

Como rodar a aplicação:

### Rodando o JAR usando Maven

1. Clone o repositório:
    ```bash
    git clone https://github.com/fiapg70/sevenfood-client-api
    cd sevenfood-client-api
    ```

2. Compile e rode o JAR:
    ```bash
    mvn clean install
    java -jar target/nome-do-jar.jar
    ```

### Configurando o Ambiente no IntelliJ IDEA

1. Abra o IntelliJ IDEA e selecione `File -> Open...` e escolha o diretório do projeto.

2. Configure as variáveis de ambiente:
    - Clique com o botão direito no projeto no painel lateral e selecione `Edit Configurations...`.
    - Clique no ícone `+` no canto superior esquerdo e selecione `Application`.
    - Configure os campos:
        - **Name**: Nome do projeto
        - **Main class**: `br.com.sevenfood.client.sevenfoodclientapi.RunApplication` (substitua `br.com.sevenfood.client.sevenfoodclientapi.RunApplication` pela sua classe principal)
        - **VM options**: `-Dspring.profiles.active=prod` (Configuração para perfil de produção)
        - **Environment variables**: Clique no ícone `...` e adicione as variáveis necessárias:
          ```properties
          API_PORT=9999
          AWS_ACCESS_KEY_ID=<<Valor>>
          AWS_REGION=<<Valor>>
          AWS_SECRET_ACCESS_KEY=<<Valor>>
          AWS_SQS_QUEUE_ARN=<<Valor>>
          AWS_SQS_QUEUE_NAME=<<Valor>>
          AWS_SQS_QUEUE_URL=<<Valor>>
          DATABASE_PASSWORD=<<Valor>>
          DATABASE_URL=jdbc:postgresql://localhost:5432/<<DatabaseValor>>
          DATABASE_USERNAME=<<Valor>>
          MAIL_HOST=<<Valor>>
          MAIL_PASSWORD=<<Valor>>
          MAIL_PORT=<<Valor>>
          MAIL_USERNAME=<<Valor>>
          SECURITY_JWT_SECRET_KEY=<<Valor>>
          SES_SMTP_PASSWORD=<<Valor>>
          SES_SMTP_USERNAME=<<Valor>>
          SNS_TOPIC_EMAIL_ARN=<<Valor>>
          API_GATEWAY_URL_PRIVACY_NOTIFICATION_STATUS=<<Valor>>
          ```

3. Rode a aplicação:
    - Clique no ícone `Run` no canto superior direito ou pressione `Shift + F10`.

## Utilizando Arquivos `.env`

Para gerenciar variáveis de ambiente usando arquivos `.env`, siga estas etapas:

1. Crie um arquivo `.env` na raiz do projeto:
    ```properties
    API_PORT=9999
    AWS_ACCESS_KEY_ID=<<Valor>>
    AWS_REGION=<<Valor>>
    AWS_SECRET_ACCESS_KEY=<<Valor>>
    AWS_SQS_QUEUE_ARN=<<Valor>>
    AWS_SQS_QUEUE_NAME=<<Valor>>
    AWS_SQS_QUEUE_URL=<<Valor>>
    DATABASE_PASSWORD=<<Valor>>
    DATABASE_URL=jdbc:postgresql://localhost:5432/<<DatabaseValor>>
    DATABASE_USERNAME=<<Valor>>
    MAIL_HOST=<<Valor>>
    MAIL_PASSWORD=<<Valor>>
    MAIL_PORT=<<Valor>>
    MAIL_USERNAME=<<Valor>>
    SECURITY_JWT_SECRET_KEY=<<Valor>>
    SES_SMTP_PASSWORD=<<Valor>>
    SES_SMTP_USERNAME=<<Valor>>
    SNS_TOPIC_EMAIL_ARN=<<Valor>>
    API_GATEWAY_URL_PRIVACY_NOTIFICATION_STATUS=<<Valor>>
    ```

2. Adicione a dependência `spring-boot-dotenv` ao seu `pom.xml`:
    ```xml
    <dependency>
        <groupId>io.github.cdimascio</groupId>
        <artifactId>java-dotenv</artifactId>
        <version>5.2.2</version>
    </dependency>
    ```

3. Configure sua aplicação para ler o arquivo `.env`. Adicione o seguinte código à classe principal ou a uma configuração:
    ```java
    import io.github.cdimascio.dotenv.Dotenv;

    @SpringBootApplication
    public class RunApplication {
        public static void main(String[] args) {
            Dotenv dotenv = Dotenv.configure().load();
            System.setProperty("DB_URL", dotenv.get("DB_URL"));
            System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
            System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
            SpringApplication.run(RunApplication.class, args);
        }
    }
    ```

4. Configure as propriedades do Spring Boot para usar as variáveis de ambiente:
    ```properties
    spring.datasource.url=${DB_URL}
    spring.datasource.username=${DB_USERNAME}
    spring.datasource.password=${DB_PASSWORD}
    ```

### Rodando PostgreSQL e SonarQube com Docker Compose

O projeto inclui um arquivo `docker-compose.yml` no pacote `infra/` para rodar o PostgreSQL e o SonarQube. Siga as instruções abaixo para configurar e rodar esses serviços:

1. Navegue até o diretório `infra`:
    ```bash
    cd infra/postgres
    # ou
    cd infra/sonar
    ```

2. Rode o Docker Compose:
    ```bash
    docker-compose up -d
    ```

3. Verifique se os serviços estão rodando:
    ```bash
    docker-compose ps
    ```

O PostgreSQL estará disponível na porta 5432 e o SonarQube na porta 9000.

### Configurando o Perfil de Produção

Para rodar o perfil de produção, adicione a opção `-Dspring.profiles.active=prod` ao rodar o Maven ou configurar no IntelliJ IDEA.

### Rodando com Maven

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```
Ambientes: prod,hom,dev

## ⚙️ Executando os testes

Explicar como executar os testes automatizados para este sistema.

### 🔩 Analise os testes de ponta a ponta

Como rodar o Sonar no docker:

```
docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community
```
URL de acesso: http://localhost:9000 <br />
<br />
Credenciais padrão:<br />
>Usuário: admin<br />
>Senha: admin<br />

### ⌨️ E testes de estilo de codificação

Para rodar o projeto com Sonar.

```
mvn clean verify sonar:sonar
```

## 🛠️ OWASP ZAP

#### Reports OWASP ZAP API Scan
Os reports de "antes" e "depois" encontram-se na pasta `/docs/zap-scanning-report`\
[Clique aqui para acessar](https://github.com/hackthon-fiap-sub/selectgearmotors-client-api/tree/main/docs/zap-scanning-report)↗️

<details>

<summary>Como escanear a API usando o OWASP ZAP?</summary>

### ZAP - API Scan

Para escanear todos os endpoints da API em busca de vulnerabilidades siga o passo a passo abaixo.

1. Execute a aplicação usando o Docker Compose;
2. Execute o comando abaixo:
```bash
docker run --name zap --network host -v $(pwd):/zap/wrk/:rw -t zaproxy/zap-stable zap-api-scan.py -t http://localhost:3000/swagger-json -f openapi -r report.html
```

> Substitua os parenteses em `$(pwd)` por chaves `${pwd}` no Windows.

O report em formato HTML será gerado no diretório atual.

[Clique aqui](https://www.zaproxy.org/docs/docker/api-scan/) para obter mais informações sobre o API Scan do ZAP.

</details>

## Relatório de Impacto à Proteção de Dados Pessoais (RIPD)

O Relatório de Impacto à Proteção de Dados Pessoais (RIPD) está disponível na pasta `/docs/RIPD`\
[Clique aqui para acessar](https://github.com/hackthon-fiap-sub/selectgearmotors-client-api/tree/main/docs/RIPD)↗️

## 🖇️ Colaborando

Por favor, leia o [COLABORACAO.md](https://gist.github.com/rogeriofontes/1dc3a8597f937001b0edd9e01972e851) para obter detalhes sobre o nosso código de conduta e o processo para nos enviar pedidos de solicitação.

## 📌 Versão

Nós usamos [SemVer](http://semver.org/) para controle de versão. Para as versões disponíveis, observe as [tags neste repositório](https://github.com/hackthon-fiap-sub/selectgearmotors-client-api/tags).

## ✒️ Autores
---
Autor Principal do projeto:

<a href="https://rogeriofontes.github.io/">
 <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/1079023?v=4" width="100px;" alt=""/>
 <br />
 <sub><b>Rogério Fontes</b></sub></a> <a href="https://rogeriofontes.github.io/" title="dev">🚀</a>


Feito com ❤️ por Rogério Fontes 👋🏽 Entre em contato!

[![Twitter Badge](https://img.shields.io/badge/-@rogeriofontes-1ca0f1?style=flat-square&labelColor=1ca0f1&logo=twitter&logoColor=white&link=https://twitter.com/rogeriofontes)](https://twitter.com/rogeriofontes) [![Linkedin Badge](https://img.shields.io/badge/-RogérioFontes-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/rogeriofontes/)](https://www.linkedin.com/in/rogeriofontes/)
<!--[![Gmail Badge](https://img.shields.io/badge/-tgmarinho@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:tgmarinho@gmail.com)](mailto:tgmarinho@gmail.com) -->

Você também pode ver a lista de todos os [colaboradores](https://github.com/orgs/hackthon-fiap-sub/projects) que participaram deste projeto.

## 📄 Licença

Este projeto está sob a licença Apache versão 2.0. Veja o arquivo [LICENSE.md](https://github.com/usuario/projeto/licenca) para detalhes.

## 🎁 Expressões de gratidão

* Conte a outras pessoas sobre este projeto 📢;
* Convide alguém da equipe para uma cerveja 🍺;
* Um agradecimento publicamente 🫂;
* etc.
---
⌨️ com ❤️ por [Rogério Fontes](https://rogeriofontes.github.io/) 😊