package japicmp.test;

import static japicmp.test.util.Helper.getArchive;
import static japicmp.test.util.Helper.getJApiClass;
import static japicmp.test.util.Helper.getJApiField;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import japicmp.cmp.JarArchiveComparator;
import japicmp.cmp.JarArchiveComparatorOptions;
import japicmp.model.AccessModifier;
import japicmp.model.JApiChangeStatus;
import japicmp.model.JApiClass;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Optional;

public class FieldsTest {
    private static List<JApiClass> jApiClasses;

    @BeforeClass
    public static void beforeClass() {
        JarArchiveComparatorOptions options = new JarArchiveComparatorOptions();
        JarArchiveComparator jarArchiveComparator = new JarArchiveComparator(options);
        jApiClasses = jarArchiveComparator.compare(getArchive("japicmp-test-v1.jar"), getArchive("japicmp-test-v2.jar"));
    }

    @Test
    public void testPrivateToPublicField() {
        JApiClass fieldsClass = getJApiClass(jApiClasses, Fields.class.getName());
        assertThat(fieldsClass.getChangeStatus(), is(JApiChangeStatus.MODIFIED));
        assertThat(getJApiField(fieldsClass.getFields(), "privateToPublicField").getChangeStatus(), is(JApiChangeStatus.MODIFIED));
        assertThat(getJApiField(fieldsClass.getFields(), "privateToPublicField").getAccessModifier().getOldModifier(), is(Optional.of(AccessModifier.PRIVATE)));
        assertThat(getJApiField(fieldsClass.getFields(), "privateToPublicField").getAccessModifier().getNewModifier(), is(Optional.of(AccessModifier.PUBLIC)));
    }

    @Test
    public void testPrivateToProtectedField() {
        JApiClass fieldsClass = getJApiClass(jApiClasses, Fields.class.getName());
        assertThat(fieldsClass.getChangeStatus(), is(JApiChangeStatus.MODIFIED));
        assertThat(getJApiField(fieldsClass.getFields(), "privateToProtectedField").getChangeStatus(), is(JApiChangeStatus.MODIFIED));
        assertThat(getJApiField(fieldsClass.getFields(), "privateToProtectedField").getAccessModifier().getOldModifier(), is(Optional.of(AccessModifier.PRIVATE)));
        assertThat(getJApiField(fieldsClass.getFields(), "privateToProtectedField").getAccessModifier().getNewModifier(), is(Optional.of(AccessModifier.PROTECTED)));
    }

    @Test
    public void testPrivateToPackageProctedField() {
        JApiClass fieldsClass = getJApiClass(jApiClasses, Fields.class.getName());
        assertThat(fieldsClass.getChangeStatus(), is(JApiChangeStatus.MODIFIED));
        assertThat(getJApiField(fieldsClass.getFields(), "privateToPackageProctedField").getChangeStatus(), is(JApiChangeStatus.MODIFIED));
        assertThat(getJApiField(fieldsClass.getFields(), "privateToPackageProctedField").getAccessModifier().getOldModifier(), is(Optional.of(AccessModifier.PRIVATE)));
        assertThat(getJApiField(fieldsClass.getFields(), "privateToPackageProctedField").getAccessModifier().getNewModifier(), is(Optional.of(AccessModifier.PACKAGE_PROTECTED)));
    }

    @Test
    public void test() {
        JApiClass fieldsClass = getJApiClass(jApiClasses, Fields.class.getName());
        assertThat(fieldsClass.getChangeStatus(), is(JApiChangeStatus.MODIFIED));
        assertThat(getJApiField(fieldsClass.getFields(), "privateRemainsPrivateField").getChangeStatus(), is(JApiChangeStatus.UNCHANGED));
        assertThat(getJApiField(fieldsClass.getFields(), "privateRemainsPrivateField").getAccessModifier().getOldModifier(), is(Optional.of(AccessModifier.PRIVATE)));
        assertThat(getJApiField(fieldsClass.getFields(), "privateRemainsPrivateField").getAccessModifier().getNewModifier(), is(Optional.of(AccessModifier.PRIVATE)));
    }
}
