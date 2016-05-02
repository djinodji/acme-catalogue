<?php

namespace AppBundle\Controller;

use AppBundle\Form\UploadType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Tests\Extension\Core\Type\CollectionTypeTest;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use AppBundle\Entity\Image;
use AppBundle\Form\ImageType;
use Symfony\Component\Form\Extension\Core\Type\CollectionType;


class UploadController extends Controller
{

    /**
     * Displays a form to edit an existing Image entity.
     *
     * @Route("/upload", name="image_upload")
     * @Method({"GET", "POST"})
     */
    public function uploadAction(Request $request)
    {
        /**dump('ici');
        exit(); **/
        $image = new Image();
        $form = $this->createFormBuilder()
            ->add('files',FileType::class, array(
                "attr"     => array(
                    "accept"   => "image/*",
                    "multiple" =>"multiple"
                )))
            ->getForm();

        $form->handleRequest($request);

        if ($form->isValid()){


            $em = $this->getDoctrine()->getManager();
            $file = $form['files']->getData();

                $tmp = $file->getPathname();

               $id = uniqid();
                copy($tmp, '../../acme.api/api/documents/' . $id . '.png');

            $stamp = imagecreatefrompng('../../acme.admin/web/bundles/app/images/logo.png');
            $im = imagecreatefrompng('../../acme.api/api/documents/' . $id . '.png');
            $marge_right = 10;
            $marge_bottom = 10;
            $sx = imagesx($stamp);
            $sy = imagesy($stamp);

            imagecopy($im, $stamp, imagesx($im) - $sx - $marge_right, imagesy($im) - $sy - $marge_bottom, 0, 0, imagesx($stamp), imagesy($stamp));

            imagepng($im, '../../acme.api/api/documents/' . $id . '.png'  , 0);
            $image->setNom($file->getClientOriginalName());

                $image->setDescription($file->getClientOriginalName());
                $image->setOriginalPath('http://rattrapage-2.estiam.com/api/documents/' . $id . '.png');
                $image->setPath('http://rattrapage-2.estiam.com/api/documents/' . $id . '.png');
            $image->setStatut(1);
                $em->persist($image);

                $em->flush();

            return $this->redirectToRoute('home');
        }
        return $this->render('AppBundle:Upload:new.html.twig', array('uploadForm'=>$form->createView()));

    }


}
