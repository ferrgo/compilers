;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

extern  _printf ; Referência para função externa que será chamada (printf de C)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

SECTION .data ; Segmento de dados
  a: dd 0 ; Declara variável global int a com 32 bits e inicializa com 0
  b: dq 0.0 ; Declara variável global double b com 64 bits e inicializa com 0.0
  igual: dd 0 ; Declara variável global boolean c com 32 bits (0 - false, 1 - true)

  const1: dq 5.3 ; Declara constante double const1 com valor 5.3

  intFormat: db "%d", 10, 0 ; String com o formato do printf %d, \n e \0
  doubleFormat: db "%.2f", 10, 0 ; String com o formato do printf %.2lf, \n e \0

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

SECTION .text ; Segmento de código
  global _WinMain@16 ; Definição do ponto de entrada do programa

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

  _fatorialNaoRecursivo: ; Inicío da função fatorialNaoRecursivo
    push ebp ; Coloca na pilha o antigo valor de ebp
    mov ebp, esp ; Salva em ebp o atual valor de esp

    sub esp, 4 ; Decrementa esp de 4 bytes (32 bits) para armazenar variável local
    push dword 1 ; Empilha o valor 1
    pop dword [ebp-4] ; Desempilha e atribui à variável local

    push dword [ebp+8] ; Empilha o par�metro
    push dword 0 ; Empilha o valor 0
    pop ebx ; Desempilha e coloca em ebx o valor 0
    pop eax ; Desempilha e coloca em eax o par�metro
    cmp eax, ebx ; Compara o valor de eax com ebx
    jle fatorialNaoRecursivo_else_1_block ; Desvia se o par�metro for <= a 0
      fatorialNaoRecursivo_while_1_begin: ; Label do in�cio do while
        push dword 1 ; Empilha o valor 1 (true)
        push dword 1 ; Empilha de novo o valor 1 (true)
        pop ebx ; Desempilha e salva em ebx o valor 1 (true)
        pop eax ; Desempilha e salva em eax o valor 1 (true)
        cmp eax, ebx ; Compara se eax � igual � ebx (true)
        jne fatorialNaoRecursivo_while_1_end ; Desvia se for diferente

        push dword [ebp-4] ; Empilha a vari�vel local
        push dword [ebp+8] ; Empilha o par�metro
        pop ebx ; Desempilha o topo e salva em ebx
        pop eax ; Desempilha o topo e salva em eax
        imul eax, ebx ; eax = eax * ebx
        push eax ; Empilha o novo valor de eax
        pop dword[ebp-4] ; Desempilha o topo da pilha e salva na vari�vel local

        push dword[ebp+8] ; Empilha o par�metro
        push dword 1 ; Empilha o valor 1
        pop ebx ; Desempilha o topo e salva em ebx
        pop eax ; Desempilha o topo e salva em eax
        sub eax, ebx ; eax = eax - ebx
        push eax ; Empilha o novo valor de eax
        pop dword[ebp+8] ; Desempeilha o topo da pilha e salva no par�metro

        push dword [ebp+8] ; Empilha o valor do par�metro
        push dword 0 ; Empilha o valor 0
        pop ebx ; Desempilha e coloca em ebx o valor 0
        pop eax ; Desempilha e coloca em eax o valor do par�metro
        cmp eax, ebx ; Compara se eax � igual � ebx
        jne fatorialNaoRecursivo_endif_2 ; Desvia se n�o for igual a 0
          jmp fatorialNaoRecursivo_while_1_end ; Se for 0, sai do while

        fatorialNaoRecursivo_endif_2:
        jmp fatorialNaoRecursivo_while_1_begin ; Volta para o in�cio do while

      fatorialNaoRecursivo_while_1_end: ; Final do while
      push dword [ebp-4] ; Empilha o valor da vari�vel local
      pop eax ; Desempilha e salva em eax o valor que ser� retornado (vari�vel local)
      mov esp, ebp ; Atualiza o valor de esp (apaga vari�veis locais)
      pop ebp ; Atualiza o valor de ebp
      ret ; Retorna

      jmp fatorialNaoRecursivo_endif_1

    fatorialNaoRecursivo_else_1_block:
      push dword 0 ; Empilha o valor 0
      pop eax ; Desempilha e salva em eax o valor que ser� retornado (0)
      mov esp, ebp ; Atualiza o valor de esp (apaga vari�veis locais)
      pop ebp ; Atualiza o valor de ebp
      ret ; Retorna

    fatorialNaoRecursivo_endif_1:

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

  _fatorialRecursivo: ; In�cio da fun��o fatorialRecursivo
    push ebp ; Coloca na pilha o antigo valor de ebp
    mov ebp, esp ; Salva em ebp o atual valor de esp

    sub esp, 4 ; Decrementa esp de 4 bytes (32 bits) para armazenar vari�vel local

    push dword [ebp+8] ; Empilha o valor do par�metro
    push dword 0 ; Empilha o valor 0
    pop ebx ; Desempilha e coloca em ebx o valor 0
    pop eax ; Desempilha e coloca em eax o par�metro
    cmp eax, ebx ; Compara o valor de eax com ebx
    jle fatorialRecursivo_else_1_block ; Desvia se o par�metro for <= a 0
      push dword [ebp+8] ; Empilha o valor do par�metro
      push dword 1 ; Empilha o valor 1
      pop ebx ; Desempilha e coloca em ebx o valor 1
      pop eax ; Desempilha e coloca em eax o valor do par�metro
      cmp eax, ebx ; Compara se eax � igual � ebx
      jne fatorialRecursivo_else_2_block ; Desvia se n�o for igual
        push dword 1 ; Empilha o valor 1
        pop eax ; Desempilha e salva em eax o valor que ser� retornado (1)
        mov esp, ebp ; Atualiza o valor de esp (apaga vari�veis locais)
        pop ebp ; Atualiza o valor de ebp
        ret ; Retorna

        jmp fatorialRecursivo_endif_2

      fatorialRecursivo_else_2_block:
        push dword [ebp+8] ; Empilha o valor do par�metro
        pop dword [ebp-4] ; Desempilha o topo da pilha e salva na vari�vel local

        push dword [ebp+8] ; Empilha o valor do par�metro
        push dword 1 ; Empilha o valor 1
        pop ebx ; Desempilha o topo da pilha e salva em ebx
        pop eax ; Desempilha o topo da pilha e salva em eax
        sub eax, ebx ; eax = eax - ebx
        push eax ; Empilha o novo valor de eax
        pop dword [ebp+8] ; Desempilha o topo da pilha e salva no par�metro

        push dword [ebp+8] ; Empilha o valor do par�metro (par�metro da recurs�o)
        call _fatorialRecursivo ; Chama a fun��o recursivamente
        add esp, 4 ; Desempilha o par�metro passado
        push eax ; Empilha o valor de eax
        pop dword [ebp+8] ; Desempilha e salva o valor retornado no par�metro

        push dword [ebp-4] ; Empilha o valor da vari�vel local
        push dword [ebp+8] ; Empilha o valor do par�metro
        pop ebx ; Desempilha o topo da pilha e salva em ebx
        pop eax ; Desempilha o topo da pilha e salva em eax
        imul eax, ebx ; eax = eax * ebx
        push eax ; Empilha o novo valor de eax
        pop dword [ebp-4] ; Desempilha o topo da pilha e salva na vari�vel local

        push dword [ebp-4] ; Empilha o valor da vari�vel local
        pop eax ; Desempilha e salva em eax o valor que ser� retornado (vari�vel local)
        mov esp, ebp ; Atualiza o valor de esp (apaga vari�veis locais)
        pop ebp ; Atualiza o valor de ebp
        ret ; Retorna

      fatorialRecursivo_endif_2:

      jmp fatorialRecursivo_endif_1

    fatorialRecursivo_else_1_block:
      push dword 0 ; Empilha o valor 0
      pop eax ; Desempilha e salva em eax o valor que ser� retornado (0)
      mov esp, ebp ; Atualiza o valor de esp (apaga vari�veis locais)
      pop ebp ; Atualiza o valor de ebp
      ret ; Retorna

    fatorialRecursivo_endif_1:

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

  _WinMain@16: ; In�cio da main do programa
    push ebp ; Coloca na pilha o antigo valor de ebp
    mov ebp, esp ; Salva em ebp o atual valor de esp

    sub esp, 16 ; Aloca espa�o para vari�veis locais: 1 double e 2 int = 16 bytes

    fld qword [const1] ; Empilha na pilha de pontos flutuantes o valor de const1
    fstp qword [ebp-16] ; Desempilha o topo da pilha e salva na vari�vel local d

    fld qword [ebp-16] ; Empilha o valor de d
    fstp qword [b] ; Desempilha e salva em b

    fld qword [b] ; Empilha o valor de b
    fld qword [ebp-16] ; Empilha o valor de d
    faddp st1 ; Desempilha dois valores, adiciona e coloca como novo topo da pilha
    fstp qword [b] ; Desempilha e salva em b

    push dword 5 ; Empilha o valor 5
    pop dword [a] ; Atribui o valor 5 � global a

    push dword [a] ; Empilha o valor de a
    call _fatorialNaoRecursivo ; Chama a fun��o fatorialNaoRecursivo
    add esp, 4 ; Limpa o valor alocado para o par�metro
    push eax ; Empilha o valor do retorno
    pop dword [ebp-8] ; Desempilha e salva em res1 (2a vari�vel local) o retorno da fun��o

    push dword [a] ; Empilha o valor de a
    call _fatorialRecursivo ; Chama a fun��o fatorialRecursivo
    add esp, 4 ; Limpa o valor alocado para o par�metro
    push eax ; Empilha o valor do retorno
    pop dword [ebp-4] ; Desempilha e salva em res2 (3a vari�vel local) o retorno da fun��o

    push dword 0 ; Empilha o valor 0
    pop dword [igual] ; Desempilha e atribui o valor 0 (false) � global igual

    push dword [ebp-8] ; Empilha o valor de res1
    push dword [ebp-4] ; Empilha o valor de res2
    pop ebx ; Coloca em ebx o valor de res2
    pop eax ; Coloca em eax o valor de res1
    cmp eax, ebx ; Compara se o valor de eax � igual ao de ebx
    jne WinMain@16_endif_1 ; Desvia se n�o forem iguais
      push dword 1 ; Empilha o valor 1
      pop dword [igual] ; Se forem iguais, desempilha e atribui 1 (true) � global igual

    WinMain@16_endif_1:

    push dword [a] ; Empilha o valor de a
    push dword intFormat ; Empilha o formato do printf para int
    call _printf ; Chama a fun��o externa printf (C)
    add esp, 8 ; Libera o espa�o dos dois par�metros

    push dword [ebp-8] ; Empilha o valor de res1
    push dword intFormat ; Empilha o formato do printf para int
    call _printf ; Chama a fun��o externa printf (C)
    add esp, 8 ; Libera o espa�o dos dois par�metros

    push dword [ebp-4] ; Empilha o valor de res2
    push dword intFormat ; Empilha o formato do printf para int
    call _printf ; Chama a fun��o externa printf (C)
    add esp, 8 ; Libera o espa�o dos dois par�metros

    push dword [igual] ; Empilha o valor de igual
    push dword intFormat ; Empilha o formato do printf para int
    call _printf ; Chama a fun��o externa printf (C)
    add esp, 8 ; Libera o espa�o dos dois par�metros

    push dword [b+4] ; Empilha a parte menos significativa de b
    push dword [b] ; Empilha a parte mais significativa de b
    push dword doubleFormat ; Empilha o formato do printf para double
    call _printf ; Chama a fun��o externa printf (C)
    add esp, 12 ; Libera o espa�o dos dois par�metros, 1 double e 1 int = 12 bytes

    mov esp, ebp ; Atualiza o valor de esp (apaga vari�veis locais)
    pop ebp ; Atualiza o valor de ebp
    mov eax, 0 ; Informa que o programa terminou sem problemas
    ret ; Retorna

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
