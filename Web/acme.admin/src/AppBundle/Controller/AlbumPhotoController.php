<?php

namespace AppBundle\Controller;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use AppBundle\Entity\AlbumPhoto;
use AppBundle\Form\AlbumPhotoType;

/**
 * AlbumPhoto controller.
 *
 * @Route("/albumphoto")
 */
class AlbumPhotoController extends Controller
{
    /**
     * Lists all AlbumPhoto entities.
     *
     * @Route("/", name="albumphoto_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $albumPhotos = $em->getRepository('AppBundle:AlbumPhoto')->findAll();
        usort($albumPhotos,array(AlbumPhoto::class, "cmp"));
        foreach ($albumPhotos as $al )
        {
            $al->albumnom=$em->getRepository('AppBundle:Album')->find($al->getAlbumId())->getNom();
            $al->imagenom=$em->getRepository('AppBundle:Image')->find($al->getImageId())->getNom();
        }
//        $albums = $em->getRepository('AppBundle:Album')->findAll();


        return $this->render('albumphoto/index.html.twig', array(
            'albumPhotos' => $albumPhotos,
        ));
    }

    /**
     * Creates a new AlbumPhoto entity.
     *
     * @Route("/new", name="albumphoto_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {

        $albumPhoto = new AlbumPhoto();
        $albumPhoto->setStatut(1);
        $form = $this->createForm('AppBundle\Form\AlbumPhotoType', $albumPhoto);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $dataal = $form->getData()->getAlbumId();
            $dataph = $form->getData()->getImageId();
            $albumPhoto->setAlbumId($dataal->getId());
            $albumPhoto->setImageId($dataph->getId());
            $em = $this->getDoctrine()->getManager();
            $em->persist($albumPhoto);
            $em->flush();

            return $this->redirectToRoute('albumphoto_show', array('id' => $albumPhoto->getId()));
        }

        return $this->render('albumphoto/new.html.twig', array(
            'albumPhoto' => $albumPhoto,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a AlbumPhoto entity.
     *
     * @Route("/{id}", name="albumphoto_show")
     * @Method("GET")
     */
    public function showAction(AlbumPhoto $albumPhoto)
    {
        $deleteForm = $this->createDeleteForm($albumPhoto);

        return $this->render('albumphoto/show.html.twig', array(
            'albumPhoto' => $albumPhoto,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing AlbumPhoto entity.
     *
     * @Route("/{id}/edit", name="albumphoto_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, AlbumPhoto $albumPhoto)
    {
        $deleteForm = $this->createDeleteForm($albumPhoto);
        $editForm = $this->createForm('AppBundle\Form\AlbumPhotoType', $albumPhoto);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($albumPhoto);
            $em->flush();

            return $this->redirectToRoute('albumphoto_edit', array('id' => $albumPhoto->getId()));
        }

        return $this->render('albumphoto/edit.html.twig', array(
            'albumPhoto' => $albumPhoto,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a AlbumPhoto entity.
     *
     * @Route("/{id}", name="albumphoto_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, AlbumPhoto $albumPhoto)
    {
        $form = $this->createDeleteForm($albumPhoto);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($albumPhoto);
            $em->flush();
        }

        return $this->redirectToRoute('albumphoto_index');
    }

    /**
     * Creates a form to delete a AlbumPhoto entity.
     *
     * @param AlbumPhoto $albumPhoto The AlbumPhoto entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(AlbumPhoto $albumPhoto)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('albumphoto_delete', array('id' => $albumPhoto->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
