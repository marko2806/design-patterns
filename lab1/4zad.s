	.file	"4zad.cpp"
	.intel_syntax noprefix
	.text
	.section	.rodata
	.type	_ZStL19piecewise_construct, @object
	.size	_ZStL19piecewise_construct, 1
_ZStL19piecewise_construct:
	.zero	1
	.local	_ZStL8__ioinit
	.comm	_ZStL8__ioinit,1,1
	.section	.text._ZN9CoolClass3setEi,"axG",@progbits,_ZN9CoolClass3setEi,comdat
	.align 2
	.weak	_ZN9CoolClass3setEi
	.type	_ZN9CoolClass3setEi, @function

//CoolClass:set(int)

_ZN9CoolClass3setEi:
.LFB1493:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	mov	QWORD PTR -8[rbp], rdi		//rbp - 8 = pb
	mov	DWORD PTR -12[rbp], esi		//rbp - 12 = x
	mov	rax, QWORD PTR -8[rbp]		//rax = rbp - 8
	mov	edx, DWORD PTR -12[rbp]		//edx = rbp - 12
	mov	DWORD PTR 8[rax], edx		//rax + 8 (_x) = x
	nop
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1493:
	.size	_ZN9CoolClass3setEi, .-_ZN9CoolClass3setEi
	.section	.text._ZN9CoolClass3getEv,"axG",@progbits,_ZN9CoolClass3getEv,comdat
	.align 2
	.weak	_ZN9CoolClass3getEv
	.type	_ZN9CoolClass3getEv, @function
_ZN9CoolClass3getEv:
.LFB1494:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	mov	QWORD PTR -8[rbp], rdi		//rbp - 8 = rdi (adresa odredisne varijable)
	mov	rax, QWORD PTR -8[rbp]		//rax = rbp - 8
	mov	eax, DWORD PTR 8[rax]		//eax = rax + 8 (eax = x);
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1494:
	.size	_ZN9CoolClass3getEv, .-_ZN9CoolClass3getEv
	.section	.text._ZN13PlainOldClass3setEi,"axG",@progbits,_ZN13PlainOldClass3setEi,comdat
	.align 2
	.weak	_ZN13PlainOldClass3setEi
	.type	_ZN13PlainOldClass3setEi, @function
_ZN13PlainOldClass3setEi:
.LFB1495:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	mov	QWORD PTR -8[rbp], rdi		//rbp - 8 sadrzi odredisnu adresu
	mov	DWORD PTR -12[rbp], esi		//rbp - 12 sadrzi vrijednost koju treba postaviti
	mov	rax, QWORD PTR -8[rbp]		//u rax se sprema odredisna adresa
	mov	edx, DWORD PTR -12[rbp]		//u edx se sprema vrijednost koja se treba postaviti
	mov	DWORD PTR [rax], edx		//edx se sprema na adresu u registru rax, odnosno _x = x
	nop
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1495:
	.size	_ZN13PlainOldClass3setEi, .-_ZN13PlainOldClass3setEi
	.section	.text._ZN4BaseC2Ev,"axG",@progbits,_ZN4BaseC5Ev,comdat
	.align 2
	.weak	_ZN4BaseC2Ev
	.type	_ZN4BaseC2Ev, @function
_ZN4BaseC2Ev:
.LFB1500:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	mov	QWORD PTR -8[rbp], rdi		//rbp - 8 = rdi
	lea	rdx, _ZTV4Base[rip+16]		//rdx = adresa tablice virtualnih funkcija
	mov	rax, QWORD PTR -8[rbp]		//rax = rbp - 8
	mov	QWORD PTR [rax], rdx		//rax = tablica virtualnih funkcija
	nop
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1500:
	.size	_ZN4BaseC2Ev, .-_ZN4BaseC2Ev
	.weak	_ZN4BaseC1Ev
	.set	_ZN4BaseC1Ev,_ZN4BaseC2Ev
	.section	.text._ZN9CoolClassC2Ev,"axG",@progbits,_ZN9CoolClassC5Ev,comdat
	.align 2
	.weak	_ZN9CoolClassC2Ev
	.type	_ZN9CoolClassC2Ev, @function
_ZN9CoolClassC2Ev:
.LFB1502:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16						//na stogu se zauzima 16 bajta
	mov	QWORD PTR -8[rbp], rdi		//rbp - 8 = rdi (adresa varijable u koju spremamo referencu na objekt)
	mov	rax, QWORD PTR -8[rbp]		//rax = rbp - 8
	mov	rdi, rax					//rdi = rax
	call	_ZN4BaseC2Ev			//u rdi je nakon izvodenja potprograma stvoren Base razred
	lea	rdx, _ZTV9CoolClass[rip+16] //inicijalizacija tablice virtualnih funkcija
	mov	rax, QWORD PTR -8[rbp]		//rax = rbp - 8
	mov	QWORD PTR [rax], rdx		//u adresu na rax spremi tablicu virtualnih funkcija
	nop
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1502:
	.size	_ZN9CoolClassC2Ev, .-_ZN9CoolClassC2Ev
	.weak	_ZN9CoolClassC1Ev
	.set	_ZN9CoolClassC1Ev,_ZN9CoolClassC2Ev
	.text
	.globl	main
	.type	main, @function
main:
.LFB1497:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	push	rbx
	//alociranje mjesta na stogu za lokalne varijable 
	sub	rsp, 40
	.cfi_offset 3, -24
	mov	rax, QWORD PTR fs:40				//rax = fs:40
	mov	QWORD PTR -24[rbp], rax				//rbp - 24 = rax
	xor	eax, eax
	//velicina prostora potrebnog za alociranje
	mov	edi, 16
	//operator new
	call	_Znwm@PLT
	//Base *pb = new CoolClass();

	mov	rbx, rax				//rax sadrzi adresu dodijeljene memorije
	mov	QWORD PTR [rbx], 0		rbx = 0			//postavljanje inicijalnih vrijednosti
	mov	DWORD PTR 8[rbx], 0		rbx + 8 = 0
	mov	rdi, rbx				rdi = rbx
	call	_ZN9CoolClassC1Ev
	
	
	

	//poc.set(42)

	mov	QWORD PTR -32[rbp], rbx			rbp - 32 = rbx
	lea	rax, -36[rbp]					//objekt se alocira na stogu
	mov	esi, 42
	mov	rdi, rax						rax = rbp - 36
	call	_ZN13PlainOldClass3setEi

	//pb -> set(42)

	//rax = adresa objekta
	mov	rax, QWORD PTR -32[rbp]
	//rax = adresa virtualne tablice
	mov	rax, QWORD PTR [rax]
	//rax = adresa funkcije koja se poziva
	mov	rax, QWORD PTR [rax]
	
	//adresa objekta pb 
	mov	rdx, QWORD PTR -32[rbp]
	mov	esi, 43
	// pb je destinacija na koju treba postaviti 43
	mov	rdi, rdx
	call	rax
	mov	eax, 0
	mov	rcx, QWORD PTR -24[rbp]
	xor	rcx, QWORD PTR fs:40
	je	.L9
	call	__stack_chk_fail@PLT
.L9:
	add	rsp, 40
	pop	rbx
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1497:
	.size	main, .-main
	.weak	_ZTV9CoolClass
	.section	.data.rel.ro.local._ZTV9CoolClass,"awG",@progbits,_ZTV9CoolClass,comdat
	.align 8
	.type	_ZTV9CoolClass, @object
	.size	_ZTV9CoolClass, 32
_ZTV9CoolClass:
	.quad	0
	.quad	_ZTI9CoolClass
	.quad	_ZN9CoolClass3setEi
	.quad	_ZN9CoolClass3getEv
	.weak	_ZTV4Base
	.section	.data.rel.ro._ZTV4Base,"awG",@progbits,_ZTV4Base,comdat
	.align 8
	.type	_ZTV4Base, @object
	.size	_ZTV4Base, 32
_ZTV4Base:
	.quad	0
	.quad	_ZTI4Base
	.quad	__cxa_pure_virtual
	.quad	__cxa_pure_virtual
	.weak	_ZTI9CoolClass
	.section	.data.rel.ro._ZTI9CoolClass,"awG",@progbits,_ZTI9CoolClass,comdat
	.align 8
	.type	_ZTI9CoolClass, @object
	.size	_ZTI9CoolClass, 24
_ZTI9CoolClass:
	.quad	_ZTVN10__cxxabiv120__si_class_type_infoE+16
	.quad	_ZTS9CoolClass
	.quad	_ZTI4Base
	.weak	_ZTS9CoolClass
	.section	.rodata._ZTS9CoolClass,"aG",@progbits,_ZTS9CoolClass,comdat
	.align 8
	.type	_ZTS9CoolClass, @object
	.size	_ZTS9CoolClass, 11
_ZTS9CoolClass:
	.string	"9CoolClass"
	.weak	_ZTI4Base
	.section	.data.rel.ro._ZTI4Base,"awG",@progbits,_ZTI4Base,comdat
	.align 8
	.type	_ZTI4Base, @object
	.size	_ZTI4Base, 16
_ZTI4Base:
	.quad	_ZTVN10__cxxabiv117__class_type_infoE+16
	.quad	_ZTS4Base
	.weak	_ZTS4Base
	.section	.rodata._ZTS4Base,"aG",@progbits,_ZTS4Base,comdat
	.type	_ZTS4Base, @object
	.size	_ZTS4Base, 6
_ZTS4Base:
	.string	"4Base"
	.text
	.type	_Z41__static_initialization_and_destruction_0ii, @function
_Z41__static_initialization_and_destruction_0ii:
.LFB1984:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16
	mov	DWORD PTR -4[rbp], edi
	mov	DWORD PTR -8[rbp], esi
	cmp	DWORD PTR -4[rbp], 1
	jne	.L12
	cmp	DWORD PTR -8[rbp], 65535
	jne	.L12
	lea	rdi, _ZStL8__ioinit[rip]
	call	_ZNSt8ios_base4InitC1Ev@PLT
	lea	rdx, __dso_handle[rip]
	lea	rsi, _ZStL8__ioinit[rip]
	mov	rax, QWORD PTR _ZNSt8ios_base4InitD1Ev@GOTPCREL[rip]
	mov	rdi, rax
	call	__cxa_atexit@PLT
.L12:
	nop
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1984:
	.size	_Z41__static_initialization_and_destruction_0ii, .-_Z41__static_initialization_and_destruction_0ii
	.type	_GLOBAL__sub_I_main, @function
_GLOBAL__sub_I_main:
.LFB1985:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	mov	esi, 65535
	mov	edi, 1
	call	_Z41__static_initialization_and_destruction_0ii
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1985:
	.size	_GLOBAL__sub_I_main, .-_GLOBAL__sub_I_main
	.section	.init_array,"aw"
	.align 8
	.quad	_GLOBAL__sub_I_main
	.hidden	__dso_handle
	.ident	"GCC: (Ubuntu 7.5.0-3ubuntu1~18.04) 7.5.0"
	.section	.note.GNU-stack,"",@progbits
