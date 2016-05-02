<?php
/**
 * Created by PhpStorm.
 * User: Djinodji
 * Date: 4/24/2016
 * Time: 10:34 PM
 */
namespace AppBundle\Controller;
// src/AppBundle/Controller/SecurityController.php

// ...
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class SecurityController extends Controller
{
    /**
     * @Route("/login", name="login")
     */
    public function loginAction(Request $request)
    {

        // Si le visiteur est d�j� identifi�, on le redirige vers l'accueil
        if ($this->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_REMEMBERED')) {
            return $this->render('AppBundle:default:home.html.twig');
        }



        // Le service authentication_utils permet de r�cup�rer le nom d'utilisateur
        // et l'erreur dans le cas o� le formulaire a d�j� �t� soumis mais �tait invalide
        // (mauvais mot de passe par exemple)
        $authenticationUtils = $this->get('security.authentication_utils');

        dump($authenticationUtils->getLastAuthenticationError());
        //exit;
        return $this->render('AppBundle:default:login.html.twig', array(
            'last_username' => $authenticationUtils->getLastUsername(),
            'error'         => $authenticationUtils->getLastAuthenticationError(),
        ));
    }

    /**
     * @Route("/logout", name="logout")
     */
    public function logoutAction()
    {
    }
}