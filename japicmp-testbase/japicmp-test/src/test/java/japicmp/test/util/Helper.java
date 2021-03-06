package japicmp.test.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import japicmp.cmp.JarArchiveComparator;
import japicmp.cmp.JarArchiveComparatorOptions;
import japicmp.config.Options;
import japicmp.model.*;
import japicmp.output.xml.XmlOutputGenerator;

public class Helper {

    public static File getArchive(String filename) {
        return new File("target" + File.separator + filename);
    }

    public static JApiClass getJApiClass(List<JApiClass> jApiClasses, String fqn) {
        for (JApiClass jApiClass : jApiClasses) {
            if (jApiClass.getFullyQualifiedName().equals(fqn)) {
                return jApiClass;
            }
        }
        throw new IllegalArgumentException("No class found with name " + fqn + ".");
    }

    public static JApiMethod getJApiMethod(List<JApiMethod> jApiMethods, String name) {
        for(JApiMethod jApiMethod : jApiMethods) {
            if(jApiMethod.getName().equals(name)) {
                return jApiMethod;
            }
        }
        throw new IllegalArgumentException("No method found with name " + name + ".");
    }

    public static JApiField getJApiField(List<JApiField> jApiFields, String name) {
    	for(JApiField jApiField : jApiFields) {
    		if(jApiField.getName().equals(name)) {
    			return jApiField;
    		}
    	}
    	throw new IllegalArgumentException("No field found with name " + name + ".");
    }

    public static JApiImplementedInterface getJApiImplementedInterface(List<JApiImplementedInterface> jApiImplementedInterfaces, String name) {
    	for(JApiImplementedInterface jApiImplementedInterface : jApiImplementedInterfaces) {
    		if(jApiImplementedInterface.getFullyQualifiedName().equals(name)) {
    			return jApiImplementedInterface;
    		}
    	}
    	throw new IllegalArgumentException("No interface found with name " + name + ".");
    }

    public static JApiAnnotation getJApiAnnotation(List<JApiAnnotation> annotations, String name) {
        for(JApiAnnotation annotation : annotations) {
            if(annotation.getFullyQualifiedName().equals(name)) {
                return annotation;
            }
        }
        throw new IllegalArgumentException("No annotation found with name " + name + ".");
    }

    public static JApiAnnotationElement getJApiAnnotationElement(List<JApiAnnotationElement> annotationElements, String name) {
        for(JApiAnnotationElement annotationElement : annotationElements) {
            if(annotationElement.getName().equals(name)) {
                return annotationElement;
            }
        }
        throw new IllegalArgumentException("No annotation element found with name " + name + ".");
    }

    public static String replaceLastDotWith$(String str) {
        int lastIndex = str.lastIndexOf('.');
        if (lastIndex > -1) {
            if (lastIndex == 0) {
                if (str.length() > 1) {
                    str = "$" + str.substring(1);
                } else {
                    str = "$";
                }
            } else {
                if (str.length() > lastIndex + 1) {
                    str = str.substring(0, lastIndex) + "$" + str.substring(lastIndex + 1);
                } else {
                    str = str.substring(0, lastIndex) + "$";
                }
            }
        }
        return str;
    }

	public static List<JApiClass> compareTestV1WithTestV2() {
		JarArchiveComparatorOptions options = new JarArchiveComparatorOptions();
		JarArchiveComparator jarArchiveComparator = new JarArchiveComparator(options);
		return jarArchiveComparator.compare(getArchive("japicmp-test-v1.jar"), getArchive("japicmp-test-v2.jar"));
	}

	public static void generateHtmlOutput(List<JApiClass> jApiClasses, String xmlOutputFile, String htmlOutputFile, boolean outputOnlyModifications, AccessModifier accessModifier) {
		XmlOutputGenerator generator = new XmlOutputGenerator();
		Options options = new Options();
		options.setXmlOutputFile(Optional.of(xmlOutputFile));
		options.setHtmlOutputFile(Optional.of(htmlOutputFile));
		options.setOutputOnlyModifications(outputOnlyModifications);
		options.setAccessModifier(Optional.of(accessModifier));
		generator.generate("/old/Path", "/new/Path", jApiClasses, options);
	}
}
