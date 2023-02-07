	.file	"5zad.cpp"
	.intel_syntax noprefix
	.text
	.section	.rodata
	.type	_ZStL19piecewise_construct, @object
	.size	_ZStL19piecewise_construct, 1
_ZStL19piecewise_construct:
	.zero	1
	.local	_ZStL8__ioinit
	.comm	_ZStL8__ioinit,1,1
	.section	.text._ZN1D4prvaEv,"axG",@progbits,_ZN1D4prvaEv,comdat
	.align 2
	.weak	_ZN1D4prvaEv
	.type	_ZN1D4prvaEv, @function
_ZN1D4prvaEv:
.LFB1493:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	mov	QWORD PTR -8[rbp], rdi
	mov	eax, 42
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1493:
	.size	_ZN1D4prvaEv, .-_ZN1D4prvaEv
	.section	.text._ZN1D5drugaEi,"axG",@progbits,_ZN1D5drugaEi,comdat
	.align 2
	.weak	_ZN1D5drugaEi
	.type	_ZN1D5drugaEi, @function
_ZN1D5drugaEi:
.LFB1494:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16
	mov	QWORD PTR -8[rbp], rdi
	mov	DWORD PTR -12[rbp], esi
	mov	rax, QWORD PTR -8[rbp]
	mov	rax, QWORD PTR [rax]
	mov	rax, QWORD PTR [rax]
	mov	rdx, QWORD PTR -8[rbp]
	mov	rdi, rdx
	call	rax
	mov	edx, eax
	mov	eax, DWORD PTR -12[rbp]
	add	eax, edx
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1494:
	.size	_ZN1D5drugaEi, .-_ZN1D5drugaEi
	.section	.text._ZN1BC2Ev,"axG",@progbits,_ZN1BC5Ev,comdat
	.align 2
	.weak	_ZN1BC2Ev
	.type	_ZN1BC2Ev, @function
_ZN1BC2Ev:
.LFB1498:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	mov	QWORD PTR -8[rbp], rdi
	lea	rdx, _ZTV1B[rip+16]
	mov	rax, QWORD PTR -8[rbp]
	mov	QWORD PTR [rax], rdx
	nop
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1498:
	.size	_ZN1BC2Ev, .-_ZN1BC2Ev
	.weak	_ZN1BC1Ev
	.set	_ZN1BC1Ev,_ZN1BC2Ev
	.section	.text._ZN1DC2Ev,"axG",@progbits,_ZN1DC5Ev,comdat
	.align 2
	.weak	_ZN1DC2Ev
	.type	_ZN1DC2Ev, @function
_ZN1DC2Ev:
.LFB1500:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16
	mov	QWORD PTR -8[rbp], rdi
	mov	rax, QWORD PTR -8[rbp]
	mov	rdi, rax
	call	_ZN1BC2Ev
	lea	rdx, _ZTV1D[rip+16]
	mov	rax, QWORD PTR -8[rbp]
	mov	QWORD PTR [rax], rdx
	nop
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1500:
	.size	_ZN1DC2Ev, .-_ZN1DC2Ev
	.weak	_ZN1DC1Ev
	.set	_ZN1DC1Ev,_ZN1DC2Ev
	.text
	.globl	_Z15ispisiRezultatev
	.type	_Z15ispisiRezultatev, @function
_Z15ispisiRezultatev:
.LFB1495:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	push	rbx
	sub	rsp, 40
	.cfi_offset 3, -24
	mov	edi, 8
	call	_Znwm@PLT
	mov	rbx, rax
	mov	QWORD PTR [rbx], 0
	mov	rdi, rbx
	call	_ZN1DC1Ev
	mov	QWORD PTR -40[rbp], rbx
	mov	rax, QWORD PTR -40[rbp]
	mov	QWORD PTR -32[rbp], rax
	mov	rax, QWORD PTR -32[rbp]
	mov	rsi, rax
	lea	rdi, _ZSt4cout[rip]
	call	_ZNSolsEPKv@PLT
	mov	rdx, rax
	mov	rax, QWORD PTR _ZSt4endlIcSt11char_traitsIcEERSt13basic_ostreamIT_T0_ES6_@GOTPCREL[rip]
	mov	rsi, rax
	mov	rdi, rdx
	call	_ZNSolsEPFRSoS_E@PLT
	mov	rax, QWORD PTR -32[rbp]
	mov	QWORD PTR -24[rbp], rax
	cmp	QWORD PTR -24[rbp], 0
	setne	al
	movzx	eax, al
	mov	esi, eax
	lea	rdi, _ZSt4cout[rip]
	call	_ZNSolsEb@PLT
	mov	rdx, rax
	mov	rax, QWORD PTR _ZSt4endlIcSt11char_traitsIcEERSt13basic_ostreamIT_T0_ES6_@GOTPCREL[rip]
	mov	rsi, rax
	mov	rdi, rdx
	call	_ZNSolsEPFRSoS_E@PLT
	mov	QWORD PTR -24[rbp], 0
	mov	rax, QWORD PTR -40[rbp]
	mov	rsi, rax
	lea	rdi, _ZSt4cout[rip]
	call	_ZNSolsEPKv@PLT
	mov	rdx, rax
	mov	rax, QWORD PTR _ZSt4endlIcSt11char_traitsIcEERSt13basic_ostreamIT_T0_ES6_@GOTPCREL[rip]
	mov	rsi, rax
	mov	rdi, rdx
	call	_ZNSolsEPFRSoS_E@PLT
	mov	rax, QWORD PTR -40[rbp]
	mov	QWORD PTR -24[rbp], rax
	cmp	QWORD PTR -24[rbp], 0
	setne	al
	movzx	eax, al
	mov	esi, eax
	lea	rdi, _ZSt4cout[rip]
	call	_ZNSolsEb@PLT
	mov	rdx, rax
	mov	rax, QWORD PTR _ZSt4endlIcSt11char_traitsIcEERSt13basic_ostreamIT_T0_ES6_@GOTPCREL[rip]
	mov	rsi, rax
	mov	rdi, rdx
	call	_ZNSolsEPFRSoS_E@PLT
	nop
	add	rsp, 40
	pop	rbx
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1495:
	.size	_Z15ispisiRezultatev, .-_Z15ispisiRezultatev
	.globl	main
	.type	main, @function
main:
.LFB1502:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	call	_Z15ispisiRezultatev
	mov	eax, 0
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1502:
	.size	main, .-main
	.weak	_ZTV1D
	.section	.data.rel.ro.local._ZTV1D,"awG",@progbits,_ZTV1D,comdat
	.align 8
	.type	_ZTV1D, @object
	.size	_ZTV1D, 32
_ZTV1D:
	.quad	0
	.quad	_ZTI1D
	.quad	_ZN1D4prvaEv
	.quad	_ZN1D5drugaEi
	.weak	_ZTV1B
	.section	.data.rel.ro._ZTV1B,"awG",@progbits,_ZTV1B,comdat
	.align 8
	.type	_ZTV1B, @object
	.size	_ZTV1B, 32
_ZTV1B:
	.quad	0
	.quad	_ZTI1B
	.quad	__cxa_pure_virtual
	.quad	__cxa_pure_virtual
	.weak	_ZTI1D
	.section	.data.rel.ro._ZTI1D,"awG",@progbits,_ZTI1D,comdat
	.align 8
	.type	_ZTI1D, @object
	.size	_ZTI1D, 24
_ZTI1D:
	.quad	_ZTVN10__cxxabiv120__si_class_type_infoE+16
	.quad	_ZTS1D
	.quad	_ZTI1B
	.weak	_ZTS1D
	.section	.rodata._ZTS1D,"aG",@progbits,_ZTS1D,comdat
	.type	_ZTS1D, @object
	.size	_ZTS1D, 3
_ZTS1D:
	.string	"1D"
	.weak	_ZTI1B
	.section	.data.rel.ro._ZTI1B,"awG",@progbits,_ZTI1B,comdat
	.align 8
	.type	_ZTI1B, @object
	.size	_ZTI1B, 16
_ZTI1B:
	.quad	_ZTVN10__cxxabiv117__class_type_infoE+16
	.quad	_ZTS1B
	.weak	_ZTS1B
	.section	.rodata._ZTS1B,"aG",@progbits,_ZTS1B,comdat
	.type	_ZTS1B, @object
	.size	_ZTS1B, 3
_ZTS1B:
	.string	"1B"
	.text
	.type	_Z41__static_initialization_and_destruction_0ii, @function
_Z41__static_initialization_and_destruction_0ii:
.LFB1990:
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
.LFE1990:
	.size	_Z41__static_initialization_and_destruction_0ii, .-_Z41__static_initialization_and_destruction_0ii
	.type	_GLOBAL__sub_I__Z15ispisiRezultatev, @function
_GLOBAL__sub_I__Z15ispisiRezultatev:
.LFB1991:
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
.LFE1991:
	.size	_GLOBAL__sub_I__Z15ispisiRezultatev, .-_GLOBAL__sub_I__Z15ispisiRezultatev
	.section	.init_array,"aw"
	.align 8
	.quad	_GLOBAL__sub_I__Z15ispisiRezultatev
	.hidden	__dso_handle
	.ident	"GCC: (Ubuntu 7.5.0-3ubuntu1~18.04) 7.5.0"
	.section	.note.GNU-stack,"",@progbits
