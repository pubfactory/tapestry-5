package org.apache.tapestry5.plastic

import java.sql.SQLException

import org.apache.tapestry5.plastic.test.NoopAdvice

import testannotations.Maybe
import testannotations.Truth

class MethodAdviceTests extends AbstractPlasticSpecification {
    def "advice for a void method"() {
        setup:

        def didInvoke = false

        def mgr = createMgr( { PlasticClass pc ->

            findMethod(pc, "aSingleMethod").addAdvice ({
                didInvoke = true

                assert it.method.name == "aSingleMethod"

                assert it.getParameter(0) == 123

                assert it.hasAnnotation(Deprecated.class) == false
                assert it.hasAnnotation(Maybe.class) == true

                assert it.getAnnotation(Maybe.class).value() == Truth.YES

                it.proceed()
            } as MethodAdvice)
        } as PlasticClassTransformer)

        when:

        def o = mgr.getClassInstantiator("testsubjects.SingleMethod").newInstance()

        then:

        didInvoke == false

        when:

        o.aSingleMethod(123)

        then:

        didInvoke == true
    }

    def "multiple advice on method with parameters and return values"() {

        setup:

        def mgr = createMgr( { PlasticClass pc ->
            findMethod(pc, "dupe").addAdvice( {

                it.setParameter(0, it.getParameter(0) + 2)
                it.proceed()
            } as MethodAdvice).addAdvice ( {

                it.setParameter(0, it.getParameter(0) * 3)
                it.proceed()

                it.setReturnValue(it.getReturnValue().toUpperCase())
            } as MethodAdvice)
        } as PlasticClassTransformer)

        def o = mgr.getClassInstantiator("testsubjects.MethodAdviceTarget").newInstance()

        expect:

        o.dupe(2, "Fam") == "FAM FAM FAM FAM FAM FAM FAM FAM FAM FAM FAM FAM"
    }

    def "method that throws exceptions"() {

        setup:

        def mgr = createMgr({ PlasticClass pc ->
            findMethod(pc, "maybeThrow").addAdvice(new NoopAdvice())
        } as PlasticClassTransformer)

        def o = mgr.getClassInstantiator("testsubjects.MethodAdviceTarget").newInstance()

        expect:

        o.maybeThrow(7L) == 7L

        when:

        o.maybeThrow(0L)

        then:

        thrown(SQLException)
    }

    def "setting return value clears checked exceptions"() {
        def mgr = createMgr({ PlasticClass pc ->
            findMethod(pc, "maybeThrow").addAdvice({  MethodInvocation mi ->

                mi.proceed()

                if (mi.didThrowCheckedException()) {
                    mi.setReturnValue(-1L)
                }
            } as MethodAdvice)
        } as PlasticClassTransformer)

        def o = mgr.getClassInstantiator("testsubjects.MethodAdviceTarget").newInstance()

        expect:

        o.maybeThrow(9L) == 9L

        o.maybeThrow(0L) == -1L
    }

    /**
     * This is important because each double/long takes up two local variable slots.
     * 
     * @return
     */
    def "method with long and double parameters"() {
        setup:

        def mgr = createMgr({ PlasticClass pc ->
            findMethod(pc, "doMath").addAdvice(new NoopAdvice())
        } as PlasticClassTransformer)

        def o = mgr.getClassInstantiator("testsubjects.WidePrimitives").newInstance()

        expect:
        "The interceptor builds proper bytecode to pass the values through"

        o.doMath(2l, 4.0d, 5, 6l) == 38d
    }
}
