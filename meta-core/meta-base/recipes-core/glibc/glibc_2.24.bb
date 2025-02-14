require glibc.inc

LIC_FILES_CHKSUM = "file://LICENSES;md5=e9a558e243b36d3209f380deb394b213 \
      file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
      file://posix/rxspencer/COPYRIGHT;md5=dc5485bb394a13b2332ec1c785f5d83a \
      file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"

DEPENDS += "gperf-native"

SRCREV ?= "ea23815a795f72035262953dad5beb03e09c17dd"

SRCBRANCH ?= "release/${PV}/master"

GLIBC_GIT_URI ?= "git://sourceware.org/git/glibc.git"
UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>\d+\.\d+(\.\d+)*)"

SRC_URI = "${GLIBC_GIT_URI};branch=${SRCBRANCH};name=glibc \
           file://0005-fsl-e500-e5500-e6500-603e-fsqrt-implementation.patch \
           file://0006-readlib-Add-OECORE_KNOWN_INTERPRETER_NAMES-to-known-.patch \
           file://0007-ppc-sqrt-Fix-undefined-reference-to-__sqrt_finite.patch \
           file://0008-__ieee754_sqrt-f-are-now-inline-functions-and-call-o.patch \
           file://0009-Quote-from-bug-1443-which-explains-what-the-patch-do.patch \
           file://0010-eglibc-run-libm-err-tab.pl-with-specific-dirs-in-S.patch \
           file://0011-__ieee754_sqrt-f-are-now-inline-functions-and-call-o.patch \
           file://0012-Make-ld-version-output-matching-grok-gold-s-output.patch \
           file://0013-sysdeps-gnu-configure.ac-handle-correctly-libc_cv_ro.patch \
           file://0014-Add-unused-attribute.patch \
           file://0015-yes-within-the-path-sets-wrong-config-variables.patch \
           file://0016-timezone-re-written-tzselect-as-posix-sh.patch \
           file://0017-Remove-bash-dependency-for-nscd-init-script.patch \
           file://0018-eglibc-Cross-building-and-testing-instructions.patch \
           file://0019-eglibc-Help-bootstrap-cross-toolchain.patch \
           file://0020-eglibc-cherry-picked-from.patch \
           file://0021-eglibc-Clear-cache-lines-on-ppc8xx.patch \
           file://0022-eglibc-Resolve-__fpscr_values-on-SH4.patch \
           file://0023-eglibc-Install-PIC-archives.patch \
           file://0024-eglibc-Forward-port-cross-locale-generation-support.patch \
           file://0025-Define-DUMMY_LOCALE_T-if-not-defined.patch \
           file://0026-build_local_scope.patch \
           file://0027-arm-mark-__startcontext-as-.cantunwind-bug-20435.patch \
           file://0028-Bug-20116-Fix-use-after-free-in-pthread_create.patch \
           file://CVE-2017-100366.patch \
           file://CVE-2017-100366_2.patch \
           file://CVE-2017-100366_3.patch \
           file://CVE-2017-100366_4.patch \
           file://CVE-2017-100366_5.patch \
           file://0001-Add-atomic_exchange_relaxed.patch \
           file://0002-Add-atomic-operations-required-by-the-new-condition-.patch \
           file://0003-Add-pretty-printers-for-the-NPTL-lock-types.patch \
           file://0004-New-condvar-implementation-that-provides-stronger-or.patch \
           file://0005-Remove-__ASSUME_REQUEUE_PI.patch \
           file://0006-Fix-atomic_fetch_xor_release.patch \
           file://CVE-2015-5180.patch \
           file://0001-glibc-reset-dl-load-write-lock-after-forking.patch \
           file://0001-Bug-4578-add-ld.so-lock-while-fork.patch \
           file://glibc-CVE-2017-12132.patch \
           file://0029-X86-64-Add-_dl_runtime_resolve_avx-512-_-opt-slow-BZ.patch \
           file://0030-x86-64-Improve-branch-predication-in-_dl_runtime_res.patch \
           file://CVE-2017-12133.patch \
           file://CVE-2017-15670.patch \
           file://CVE-2017-15671.patch \
           file://CVE-2017-15671-2.patch \
           file://CVE-2017-15804.patch \
           file://TCWRLGCC-282.patch \
           file://fix_glibc_2.24_build_error.patch \
           file://0031-elf-Check-for-empty-tokens-before-dynamic-string-tok.patch \
           file://CVE-2018-1000001.patch \
           file://CVE-2018-6485_CVE-2018-6551.patch \
           file://CVE-2017-18269.patch \
           file://CVE-2018-11236.patch \
           file://CVE-2018-11237.patch \
"

SRC_URI += "\
           file://etc/ld.so.conf \
           file://generate-supported.mk \
           file://0001-locale-fix-hard-coded-reference-to-gcc-E.patch \
           "

SRC_URI_append_class-nativesdk = "\
           file://0001-nativesdk-glibc-Look-for-host-system-ld.so.cache-as-.patch \
           file://0002-nativesdk-glibc-Fix-buffer-overrun-with-a-relocated-.patch \
           file://0003-nativesdk-glibc-Raise-the-size-of-arrays-containing-.patch \
           file://0004-nativesdk-glibc-Allow-64-bit-atomics-for-x86.patch \
"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build-${TARGET_SYS}"

PACKAGES_DYNAMIC = ""

# the -isystem in bitbake.conf screws up glibc do_stage
BUILD_CPPFLAGS = "-I${STAGING_INCDIR_NATIVE}"
TARGET_CPPFLAGS = "-I${STAGING_DIR_TARGET}${includedir}"

GLIBC_BROKEN_LOCALES = ""
#
# We will skip parsing glibc when target system C library selection is not glibc
# this helps in easing out parsing for non-glibc system libraries
#
COMPATIBLE_HOST_libc-musl_class-target = "null"
COMPATIBLE_HOST_libc-uclibc_class-target = "null"

EXTRA_OECONF = "--enable-kernel=${OLDEST_KERNEL} \
                --without-cvs --disable-profile \
                --disable-debug --without-gd \
                --enable-clocale=gnu \
                --enable-add-ons \
                --with-headers=${STAGING_INCDIR} \
                --without-selinux \
                --enable-obsolete-rpc \
                ${GLIBC_EXTRA_OECONF}"

EXTRA_OECONF += "${@get_libc_fpu_setting(bb, d)}"
EXTRA_OECONF += "${@bb.utils.contains('DISTRO_FEATURES', 'libc-inet-anl', '--enable-nscd', '--disable-nscd', d)}"


do_patch_append() {
    bb.build.exec_func('do_fix_readlib_c', d)
}

do_fix_readlib_c () {
	sed -i -e 's#OECORE_KNOWN_INTERPRETER_NAMES#${EGLIBC_KNOWN_INTERPRETER_NAMES}#' ${S}/elf/readlib.c
}

do_configure () {
# override this function to avoid the autoconf/automake/aclocal/autoheader
# calls for now
# don't pass CPPFLAGS into configure, since it upsets the kernel-headers
# version check and doesn't really help with anything
        (cd ${S} && gnu-configize) || die "failure in running gnu-configize"
        find ${S} -name "configure" | xargs touch
        CPPFLAGS="" oe_runconf
}

rpcsvc = "bootparam_prot.x nlm_prot.x rstat.x \
	  yppasswd.x klm_prot.x rex.x sm_inter.x mount.x \
	  rusers.x spray.x nfs_prot.x rquota.x key_prot.x"

do_compile () {
	# -Wl,-rpath-link <staging>/lib in LDFLAGS can cause breakage if another glibc is in staging
	unset LDFLAGS
	base_do_compile
	(
		cd ${S}/sunrpc/rpcsvc
		for r in ${rpcsvc}; do
			h=`echo $r|sed -e's,\.x$,.h,'`
			rm -f $h
			${B}/sunrpc/cross-rpcgen -h $r -o $h || bbwarn "${PN}: unable to generate header for $r"
		done
	)
	echo "Adjust ldd script"
	if [ -n "${RTLDLIST}" ]
	then
		prevrtld=`cat ${B}/elf/ldd | grep "^RTLDLIST=" | sed 's#^RTLDLIST="\?\([^"]*\)"\?$#\1#'`
		if [ "${prevrtld}" != "${RTLDLIST}" ]
		then
			sed -i ${B}/elf/ldd -e "s#^RTLDLIST=.*\$#RTLDLIST=\"${prevrtld} ${RTLDLIST}\"#"
		fi
	fi

}

# Use the host locale archive when built for nativesdk so that we don't need to
# ship a complete (100MB) locale set.
do_compile_prepend_class-nativesdk() {
    echo "complocaledir=/usr/lib/locale" >> ${S}/configparms
}

do_compile_prepend_class-target() {
    echo "complocaledir=${localedir}" >> ${S}/configparms
}

require glibc-package.inc

BBCLASSEXTEND = "nativesdk"
